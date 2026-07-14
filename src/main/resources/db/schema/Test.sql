-- ========================================================
--  V1
--  Module: Tenancy
-- ========================================================

CREATE TABLE tenancy.tenants (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    name text NOT NULL,
    status text NOT NULL DEFAULT 'active' CHECK (status IN ('active','suspended','deleted')),
    data_region text NOT NULL,
    default_language text NOT NULL,
    settings jsonb NOT NULL DEFAULT '{}',
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz
);

CREATE TRIGGER trg_tenants_updated BEFORE UPDATE ON tenancy.tenants
    FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.agents (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
    el_agent_id text,
    name text NOT NULL,
    config jsonb NOT NULL DEFAULT '{}',
    language text NOT NULL,
    version integer NOT NULL DEFAULT 1,
    status text NOT NULL DEFAULT 'active' CHECK (status IN ('active','disabled','archived')),
    created_at  timestamptz NOT NULL DEFAULT now(),
    updated_at  timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_agents_tenant ON tenancy.agents (tenant_id);

CREATE TRIGGER trg_agents_updated BEFORE UPDATE ON tenancy.agents
    FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.agent_versions (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
    agent_id uuid NOT NULL REFERENCES tenancy.agents(id) ON DELETE CASCADE,
    version integer NOT NULL,
    config jsonb NOT NULL,
    changed_by text,
    created_at timestamptz NOT NULL DEFAULT now(),

    UNIQUE (agent_id, version)
);


CREATE TABLE tenancy.phone_numbers (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
    agent_id uuid NOT NULL REFERENCES tenancy.agents(id),
    e164 text NOT NULL,
    el_number_id text,
    capabilities text[] NOT NULL DEFAULT '{inbound}',
    status text NOT NULL DEFAULT 'active' CHECK (status IN ('active','inactive')),
    created_at timestamptz NOT NULL DEFAULT now(),

    UNIQUE (e164)
);

CREATE INDEX idx_numbers_tenant ON tenancy.phone_numbers (tenant_id);


CREATE TABLE tenancy.tools (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
    agent_id uuid NOT NULL REFERENCES tenancy.agents(id) ON DELETE CASCADE,
    name text NOT NULL,
    description text NOT NULL,
    param_schema jsonb NOT NULL,
    vertical_url text NOT NULL,
    enabled boolean NOT NULL DEFAULT true,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),

    UNIQUE (agent_id, name)
);

CREATE INDEX idx_tools_tenant ON tenancy.tools (tenant_id);

CREATE TRIGGER trg_tools_updated BEFORE UPDATE ON tenancy.tools
    FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.tool_secrets (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
    tool_id uuid NOT NULL REFERENCES tenancy.tools(id) ON DELETE CASCADE,
    secret_ref text NOT NULL,
    status text NOT NULL DEFAULT 'active' CHECK (status IN ('active','retiring')),
    created_at timestamptz NOT NULL DEFAULT now(),
    retire_after timestamptz
);

CREATE INDEX idx_tool_secrets ON tenancy.tool_secrets (tool_id, status);