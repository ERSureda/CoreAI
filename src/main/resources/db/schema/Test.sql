-- ========================================================
--  V1
--  Module: Tenancy
-- ========================================================

CREATE TYPE tenancy.tenant_status AS ENUM ('ACTIVE', 'SUSPENDED', 'DELETED');
CREATE TYPE tenancy.agent_status AS ENUM ('ACTIVE', 'DISABLED', 'ARCHIVED');
CREATE TYPE tenancy.phone_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE tenancy.phone_capability AS ENUM ('INBOUND', 'OUTBOUND');
CREATE TYPE tenancy.tool_secret_status AS ENUM ('ACTIVE', 'RETIRING');

CREATE TABLE tenancy.tenants (
                                 id UUID PRIMARY KEY,
                                 name varchar(100) NOT NULL,
                                 status tenancy.tenant_status NOT NULL DEFAULT 'ACTIVE',
                                 data_region varchar(50) NOT NULL,
                                 default_language varchar(10) NOT NULL,
                                 settings jsonb NOT NULL DEFAULT '{}',
                                 created_at timestamptz NOT NULL DEFAULT now(),
                                 updated_at timestamptz NOT NULL DEFAULT now(),
                                 deleted_at timestamptz
);

CREATE TRIGGER trg_tenants_updated BEFORE UPDATE ON tenancy.tenants
    FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.agents (
                                id UUID PRIMARY KEY,
                                tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
                                el_agent_id varchar(100),
                                name varchar(100) NOT NULL,
                                config jsonb NOT NULL DEFAULT '{}',
                                language varchar(10) NOT NULL,
                                version integer NOT NULL DEFAULT 1,
                                status tenancy.agent_status NOT NULL DEFAULT 'ACTIVE',
                                created_at  timestamptz NOT NULL DEFAULT now(),
                                updated_at  timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX idx_agents_tenant ON tenancy.agents (tenant_id);

CREATE TRIGGER trg_agents_updated BEFORE UPDATE ON tenancy.agents
    FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.agent_versions (
                                        id UUID PRIMARY KEY,
                                        tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
                                        agent_id uuid NOT NULL REFERENCES tenancy.agents(id) ON DELETE CASCADE,
                                        version integer NOT NULL,
                                        config jsonb NOT NULL,
                                        changed_by varchar(100),
                                        created_at timestamptz NOT NULL DEFAULT now(),

                                        UNIQUE (agent_id, version)
);


CREATE TABLE tenancy.phone_numbers (
                                       id UUID PRIMARY KEY,
                                       tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
                                       agent_id uuid NOT NULL REFERENCES tenancy.agents(id),
                                       e164 varchar(15) NOT NULL,
                                       el_number_id varchar(100),
                                       capabilities tenancy.phone_capability[] NOT NULL DEFAULT ARRAY['INBOUND'],
                                       status tenancy.phone_status NOT NULL DEFAULT 'ACTIVE',
                                       created_at timestamptz NOT NULL DEFAULT now(),

                                       UNIQUE (e164)
);

CREATE INDEX idx_numbers_tenant ON tenancy.phone_numbers (tenant_id);


CREATE TABLE tenancy.tools (
                               id UUID PRIMARY KEY,
                               tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
                               agent_id uuid NOT NULL REFERENCES tenancy.agents(id) ON DELETE CASCADE,
                               name varchar(100) NOT NULL,
                               description varchar(500) NOT NULL,
                               param_schema jsonb NOT NULL,
                               vertical_url varchar(255) NOT NULL,
                               enabled boolean NOT NULL DEFAULT true,
                               created_at timestamptz NOT NULL DEFAULT now(),
                               updated_at timestamptz NOT NULL DEFAULT now(),

                               UNIQUE (agent_id, name)
);

CREATE INDEX idx_tools_tenant ON tenancy.tools (tenant_id);

CREATE TRIGGER trg_tools_updated BEFORE UPDATE ON tenancy.tools
    FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();


CREATE TABLE tenancy.tool_secrets (
                                      id UUID PRIMARY KEY,
                                      tenant_id uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
                                      tool_id uuid NOT NULL REFERENCES tenancy.tools(id) ON DELETE CASCADE,
                                      secret_ref varchar(255) NOT NULL,
                                      status tenancy.tool_secret_status NOT NULL DEFAULT 'ACTIVE',
                                      created_at timestamptz NOT NULL DEFAULT now(),
                                      retire_after timestamptz
);

CREATE INDEX idx_tool_secrets ON tenancy.tool_secrets (tool_id, status);
