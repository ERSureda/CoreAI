-- ========================================================
--  V1
--  Module: tenancy (Core Multi-Tenant Architecture)
--  Goal: Manage isolation, configuration, and state for SaaS tenants:
--      - Separate physical instances by bounded context with tenant_id correlation
--      - Administer agent versions, languages, and telephony settings per tenant
--      - Handle secure tool integrations and secrets for each tenant
-- ========================================================

CREATE SCHEMA IF NOT EXISTS tenancy;

CREATE TYPE tenancy.tenant_status AS ENUM ('ACTIVE', 'SUSPENDED', 'DELETED');
CREATE TYPE tenancy.agent_status AS ENUM ('ACTIVE', 'DISABLED', 'ARCHIVED');
CREATE TYPE tenancy.phone_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE tenancy.phone_capability AS ENUM ('INBOUND', 'OUTBOUND');
CREATE TYPE tenancy.tool_secret_status AS ENUM ('ACTIVE', 'RETIRING');

CREATE TABLE tenancy.tenants (
	id UUID PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	status tenancy.tenant_status NOT NULL DEFAULT 'ACTIVE',
	data_region VARCHAR(50) NOT NULL,
	default_language VARCHAR(10) NOT NULL,
	settings JSONB NOT NULL DEFAULT '{}',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	deleted_at TIMESTAMPTZ
);

CREATE TRIGGER trg_tenants_updated BEFORE UPDATE ON tenancy.tenants
	FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.agents (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	el_agent_id VARCHAR(100),
	name VARCHAR(100) NOT NULL,
	config JSONB NOT NULL DEFAULT '{}',
	language VARCHAR(10) NOT NULL,
	version INTEGER NOT NULL DEFAULT 1,
	status tenancy.agent_status NOT NULL DEFAULT 'ACTIVE',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_agents_tenant ON tenancy.agents(tenant_id);

CREATE TRIGGER trg_agents_updated BEFORE UPDATE ON tenancy.agents
	FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.agent_versions (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	agent_id UUID NOT NULL REFERENCES tenancy.agents(id) ON DELETE CASCADE,
	version INTEGER NOT NULL,
	config JSONB NOT NULL,
	changed_by VARCHAR(100),
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (agent_id, version)
);


CREATE TABLE tenancy.phone_numbers (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	agent_id UUID NOT NULL REFERENCES tenancy.agents(id),
	e164 VARCHAR(15) NOT NULL,
	el_number_id VARCHAR(100),
	capabilities tenancy.phone_capability[] NOT NULL DEFAULT ARRAY['INBOUND'],
	status tenancy.phone_status NOT NULL DEFAULT 'ACTIVE',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (e164)
);

CREATE INDEX idx_numbers_tenant ON tenancy.phone_numbers(tenant_id);


CREATE TABLE tenancy.tools (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	agent_id UUID NOT NULL REFERENCES tenancy.agents(id) ON DELETE CASCADE,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(500) NOT NULL,
	param_schema JSONB NOT NULL,
	vertical_url VARCHAR(255) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (agent_id, name)
);

CREATE INDEX idx_tools_tenant ON tenancy.tools(tenant_id);

CREATE TRIGGER trg_tools_updated BEFORE UPDATE ON tenancy.tools
	FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.tool_secrets (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	tool_id UUID NOT NULL REFERENCES tenancy.tools(id) ON DELETE CASCADE,
	secret_ref VARCHAR(255) NOT NULL,
	status tenancy.tool_secret_status NOT NULL DEFAULT 'ACTIVE',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	retire_after TIMESTAMPTZ
);

CREATE INDEX idx_tool_secrets ON tenancy.tool_secrets(tool_id, status);


DO $rls$
DECLARE
  t TEXT;
  tables TEXT[] := ARRAY[
    'tenancy.agents','tenancy.agent_versions','tenancy.phone_numbers',
    'tenancy.tools','tenancy.tool_secrets'
  ];
BEGIN
  FOREACH t IN ARRAY tables LOOP
    EXECUTE format('ALTER TABLE %s ENABLE ROW LEVEL SECURITY', t);
    EXECUTE format($p$
      CREATE POLICY tenant_isolation ON %s
      USING (tenant_id = NULLIF(current_setting('app.current_tenant', true), '')::UUID)
    $p$, t);
  END LOOP;
END
$rls$;

-- ========================================================
--  V1
--  Module: platform (Cross-Cutting Platform Utilities)
--  Goal: Provide foundational tools, credentials, and compliance features:
--      - Manage machine-to-machine API credentials and scoped permissions
--      - Handle reliable webhook deliveries and internal outbox transactional messaging
--      - Enforce compliance, audit logs, and GDPR erasure requests
-- ========================================================

CREATE SCHEMA IF NOT EXISTS platform;

DO $do$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'uuidv7'
  ) THEN
    CREATE FUNCTION public.uuidv7() RETURNS UUID
    LANGUAGE plpgsql VOLATILE AS $f$
    DECLARE
      v_ts_ms BIGINT;
      v_bytes BYTEA;
    BEGIN
      v_ts_ms := floor(extract(epoch from clock_timestamp()) * 1000)::BIGINT;
      v_bytes := substring(int8send(v_ts_ms) from 3 for 6) || substring(uuid_send(gen_random_uuid()) from 7 for 10);
      v_bytes := set_byte(v_bytes, 6, (get_byte(v_bytes, 6) & 15) | 112);
      v_bytes := set_byte(v_bytes, 8, (get_byte(v_bytes, 8) & 63) | 128);
      RETURN encode(v_bytes, 'hex')::UUID;
    END;
    $f$;
  END IF;
END
$do$;

CREATE OR REPLACE FUNCTION platform.set_updated_at() RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at := now();
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION platform.create_month_partition(p_schema TEXT, p_table TEXT, p_month DATE) RETURNS VOID AS $$
DECLARE
  v_from DATE := date_trunc('month', p_month)::DATE;
  v_to   DATE := (date_trunc('month', p_month) + interval '1 month')::DATE;
  v_name TEXT := format('%s_%s', p_table, to_char(v_from, 'YYYYMM'));
BEGIN
  EXECUTE format(
    'CREATE TABLE IF NOT EXISTS %I.%I PARTITION OF %I.%I FOR VALUES FROM (%L) TO (%L)',
    p_schema, v_name, p_schema, p_table, v_from, v_to
  );
END;
$$ LANGUAGE plpgsql;

CREATE TABLE platform.api_credentials (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	name TEXT NOT NULL,
	scope TEXT NOT NULL CHECK (scope IN ('platform', 'tenant', 'reseller')),
	tenant_id UUID REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	key_prefix TEXT NOT NULL,
	key_hash TEXT NOT NULL,
	permissions TEXT[] NOT NULL DEFAULT '{}',
	status TEXT NOT NULL DEFAULT 'active' CHECK (status IN ('active', 'revoked')),
	expires_at TIMESTAMPTZ,
	last_used_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CONSTRAINT scope_tenant_chk CHECK ((scope = 'platform' AND tenant_id IS NULL) OR (scope IN ('tenant', 'reseller') AND tenant_id IS NOT NULL))
);

CREATE UNIQUE INDEX uq_api_key_prefix ON platform.api_credentials(key_prefix);
CREATE INDEX idx_api_cred_tenant ON platform.api_credentials(tenant_id) WHERE status = 'active';


CREATE TABLE platform.webhook_deliveries (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	source TEXT NOT NULL,
	kind TEXT NOT NULL CHECK (kind IN ('personalization', 'dispatch', 'post_call')),
	external_id TEXT,
	payload JSONB NOT NULL,
	signature TEXT,
	status TEXT NOT NULL DEFAULT 'received' CHECK (status IN ('received', 'processed', 'failed', 'dead_letter')),
	attempts INTEGER NOT NULL DEFAULT 0,
	last_error TEXT,
	received_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	processed_at TIMESTAMPTZ,
	UNIQUE (source, kind, external_id)
);

CREATE INDEX idx_webhook_pending ON platform.webhook_deliveries(status) WHERE status IN ('received', 'failed');


CREATE TABLE platform.outbox (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	aggregate TEXT NOT NULL,
	event_type TEXT NOT NULL,
	payload JSONB NOT NULL,
	status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending', 'processed', 'failed')),
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	processed_at TIMESTAMPTZ
);

CREATE INDEX idx_outbox_pending ON platform.outbox(created_at) WHERE status = 'pending';


CREATE TABLE platform.audit_log (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	tenant_id UUID,
	actor_id TEXT NOT NULL,
	actor_credential_id UUID REFERENCES platform.api_credentials(id),
	action TEXT NOT NULL,
	target TEXT NOT NULL,
	metadata JSONB NOT NULL DEFAULT '{}',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_audit_tenant ON platform.audit_log(tenant_id, created_at DESC);


CREATE TABLE platform.erasure_requests (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id),
	subject_ref TEXT NOT NULL,
	status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending', 'completed', 'rejected')),
	requested_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	completed_at TIMESTAMPTZ
);

-- ========================================================
--  V1
--  Module: dispatch (Event Broker and Dispatching)
--  Goal: Act as a generic bridge, providing idempotency and event auditing:
--      - Maintain an append-only log of all dispatched events for high volume
--      - Ensure global idempotency to avoid double-processing via external agents
--      - Isolate physical routing complexities from the core AI engine
-- ========================================================

CREATE SCHEMA IF NOT EXISTS dispatch;

CREATE TABLE dispatch.dispatch_events (
	id UUID NOT NULL DEFAULT uuidv7(),
	tenant_id UUID NOT NULL,
	event_id TEXT NOT NULL,
	conversation_id TEXT,
	tool_name TEXT NOT NULL,
	arguments JSONB NOT NULL,
	status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending', 'confirmed', 'failed', 'timeout')),
	response JSONB,
	latency_ms INTEGER,
	attempts INTEGER NOT NULL DEFAULT 0,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	completed_at TIMESTAMPTZ,
	PRIMARY KEY (id, created_at)
) PARTITION BY RANGE (created_at);

CREATE INDEX idx_dispatch_tenant ON dispatch.dispatch_events(tenant_id, created_at DESC);


CREATE TABLE dispatch.dispatch_events_default PARTITION OF dispatch.dispatch_events DEFAULT;


CREATE TABLE dispatch.idempotency_keys (
	tenant_id UUID NOT NULL,
	event_id TEXT NOT NULL,
	result JSONB,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	expires_at TIMESTAMPTZ NOT NULL,
	PRIMARY KEY (tenant_id, event_id)
);

CREATE INDEX idx_idempotency_expiry ON dispatch.idempotency_keys(expires_at);


DO $part$
DECLARE
  m DATE;
BEGIN
  FOR m IN
    SELECT generate_series(
      date_trunc('month', now()) - interval '1 month',
      date_trunc('month', now()) + interval '2 month',
      interval '1 month'
    )::DATE
  LOOP
    PERFORM platform.create_month_partition('dispatch','dispatch_events', m);
  END LOOP;
END
$part$;

-- ========================================================
--  V1
--  Module: billing (Subscriptions, Quotas and Ledger)
--  Goal: Oversee plans, usage, and financial records for all tenants:
--      - Monitor real-time quota consumption (minutes and dispatches)
--      - Manage commercial plans, custom overrides, and active subscriptions
--      - Maintain an immutable, PII-free economic ledger for invoicing
-- ========================================================

CREATE SCHEMA IF NOT EXISTS billing;

CREATE TABLE billing.plans (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	code TEXT NOT NULL UNIQUE,
	name TEXT NOT NULL,
	visibility TEXT NOT NULL DEFAULT 'public' CHECK (visibility IN ('public', 'private')),
	owner_tenant_id UUID REFERENCES tenancy.tenants(id),
	included_minutes INTEGER NOT NULL DEFAULT 0,
	included_dispatches INTEGER,
	max_agents INTEGER,
	max_concurrent_calls INTEGER,
	overage_cents_per_min INTEGER NOT NULL DEFAULT 0,
	hard_cap BOOLEAN NOT NULL DEFAULT false,
	monthly_price_cents INTEGER NOT NULL DEFAULT 0,
	currency TEXT NOT NULL DEFAULT 'EUR',
	stripe_price_id TEXT,
	active BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CONSTRAINT plan_visibility_owner_chk CHECK ((visibility = 'public' AND owner_tenant_id IS NULL) OR (visibility = 'private' AND owner_tenant_id IS NOT NULL))
);


CREATE TABLE billing.subscriptions (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
	plan_id UUID NOT NULL REFERENCES billing.plans(id),
	stripe_subscription_id TEXT,
	overrides JSONB NOT NULL DEFAULT '{}',
	status TEXT NOT NULL DEFAULT 'active' CHECK (status IN ('active', 'past_due', 'suspended', 'canceled')),
	current_period_start TIMESTAMPTZ NOT NULL,
	current_period_end TIMESTAMPTZ NOT NULL,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX uq_active_subscription ON billing.subscriptions(tenant_id) WHERE status = 'active';

CREATE TRIGGER trg_subscriptions_updated BEFORE UPDATE ON billing.subscriptions
	FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE billing.quota_counters (
	tenant_id UUID NOT NULL,
	period TEXT NOT NULL,
	minutes_used NUMERIC(12, 2) NOT NULL DEFAULT 0,
	dispatches_used INTEGER NOT NULL DEFAULT 0,
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	PRIMARY KEY (tenant_id, period)
);


CREATE TABLE billing.call_records (
	id UUID NOT NULL DEFAULT uuidv7(),
	tenant_id UUID NOT NULL,
	conversation_id TEXT NOT NULL,
	agent_id UUID,
	caller_e164 TEXT,
	direction TEXT NOT NULL DEFAULT 'inbound' CHECK (direction IN ('inbound', 'outbound')),
	started_at TIMESTAMPTZ NOT NULL,
	ended_at TIMESTAMPTZ NOT NULL,
	duration_sec INTEGER NOT NULL,
	el_cost_cents INTEGER,
	llm_tokens INTEGER,
	transcript_ref TEXT,
	pii_scrubbed_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	PRIMARY KEY (id, started_at),
	UNIQUE (conversation_id, started_at),
	CONSTRAINT call_time_chk CHECK (ended_at >= started_at),
	CONSTRAINT call_duration_chk CHECK (duration_sec >= 0)
) PARTITION BY RANGE (started_at);

CREATE INDEX idx_calls_tenant ON billing.call_records(tenant_id, started_at DESC);


CREATE TABLE billing.call_records_default PARTITION OF billing.call_records DEFAULT;


CREATE TABLE billing.usage_ledger (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	tenant_id UUID NOT NULL,
	call_record_id UUID NOT NULL,
	period TEXT NOT NULL,
	billable_sec INTEGER NOT NULL,
	amount_cents INTEGER NOT NULL,
	is_overage BOOLEAN NOT NULL DEFAULT false,
	currency TEXT NOT NULL DEFAULT 'EUR',
	reported BOOLEAN NOT NULL DEFAULT false,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_ledger_unreported ON billing.usage_ledger(tenant_id, period) WHERE reported = false;


CREATE TABLE billing.invoices (
	id UUID PRIMARY KEY DEFAULT uuidv7(),
	tenant_id UUID NOT NULL REFERENCES tenancy.tenants(id),
	period TEXT NOT NULL,
	stripe_invoice_id TEXT,
	amount_cents INTEGER NOT NULL,
	currency TEXT NOT NULL DEFAULT 'EUR',
	status TEXT NOT NULL DEFAULT 'draft' CHECK (status IN ('draft', 'open', 'paid', 'void', 'uncollectible')),
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (tenant_id, period)
);

CREATE TRIGGER trg_invoices_updated BEFORE UPDATE ON billing.invoices
	FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE VIEW billing.effective_entitlements AS
SELECT
	s.tenant_id,
	s.plan_id,
	p.code AS plan_code,
	COALESCE((s.overrides->>'included_minutes')::INTEGER, p.included_minutes) AS included_minutes,
	COALESCE((s.overrides->>'included_dispatches')::INTEGER, p.included_dispatches) AS included_dispatches,
	COALESCE((s.overrides->>'max_agents')::INTEGER, p.max_agents) AS max_agents,
	COALESCE((s.overrides->>'max_concurrent_calls')::INTEGER, p.max_concurrent_calls) AS max_concurrent_calls,
	COALESCE((s.overrides->>'overage_cents_per_min')::INTEGER, p.overage_cents_per_min) AS overage_cents_per_min,
	COALESCE((s.overrides->>'hard_cap')::BOOLEAN, p.hard_cap) AS hard_cap,
	s.current_period_start,
	s.current_period_end
FROM billing.subscriptions s
JOIN billing.plans p ON p.id = s.plan_id
WHERE s.status = 'active';


DO $rls$
DECLARE
  t TEXT;
  tables TEXT[] := ARRAY[
    'billing.subscriptions','billing.quota_counters','billing.call_records',
    'billing.usage_ledger','billing.invoices'
  ];
BEGIN
  FOREACH t IN ARRAY tables LOOP
    EXECUTE format('ALTER TABLE %s ENABLE ROW LEVEL SECURITY', t);
    EXECUTE format($p$
      CREATE POLICY tenant_isolation ON %s
      USING (tenant_id = NULLIF(current_setting('app.current_tenant', true), '')::UUID)
    $p$, t);
  END LOOP;
END
$rls$;


DO $part$
DECLARE
  m DATE;
BEGIN
  FOR m IN
    SELECT generate_series(
      date_trunc('month', now()) - interval '1 month',
      date_trunc('month', now()) + interval '2 month',
      interval '1 month'
    )::DATE
  LOOP
    PERFORM platform.create_month_partition('billing','call_records', m);
  END LOOP;
END
$part$;
