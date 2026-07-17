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
