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
