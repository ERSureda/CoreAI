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
