-- ============================================================================
--  VOICE AI GATEWAY · CORE API — Esquema de base de datos (PostgreSQL)
-- ----------------------------------------------------------------------------
--  Objetivo:  Core agnóstico y multi-tenant. NO contiene lógica ni datos de
--             negocio (eso vive en las verticales) ni procesamiento de voz
--             (eso vive en ElevenLabs). Solo: identidad, configuración,
--             contrato/dispatch, uso, planes/facturación y transversales.
--
--  Motor:     PostgreSQL 18+ recomendado (uuidv7() nativo). Se incluye un
--             fallback para 13–17. Sin dependencias de extensiones externas.
--
--  Esquemas:  tenancy · dispatch · billing · platform
--             (separación física por bounded context; tenant_id correlaciona)
--
--  Convenciones:
--    · PK en UUID v7 (ordenado en el tiempo → localidad de índice, sin frag.).
--    · Toda tabla mutable: updated_at (trigger) y, donde aplica, version (lock
--      optimista). Config con soft-delete; eventos/ledger append-only.
--    · Aislamiento: tenant_id + Row-Level Security en tablas tenant-scoped.
--    · Alto volumen (dispatch_events, call_records) particionado por tiempo.
-- ============================================================================

CREATE SCHEMA IF NOT EXISTS tenancy;
CREATE SCHEMA IF NOT EXISTS dispatch;
CREATE SCHEMA IF NOT EXISTS billing;
CREATE SCHEMA IF NOT EXISTS platform;

-- ============================================================================
--  0 · UTILIDADES DE PLATAFORMA
-- ============================================================================

-- 0.1 · uuidv7() nativo en PG18+. Fallback portable (13–17) si no existe.
DO $do$
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM pg_proc WHERE proname = 'uuidv7'
  ) THEN
    CREATE FUNCTION public.uuidv7() RETURNS uuid
    LANGUAGE plpgsql VOLATILE AS $f$
    DECLARE
      v_ts_ms bigint;
      v_bytes bytea;
    BEGIN
      v_ts_ms := floor(extract(epoch from clock_timestamp()) * 1000)::bigint;
      -- 6 bytes de timestamp (ms) + 10 bytes aleatorios (de un uuid v4)
      v_bytes := substring(int8send(v_ts_ms) from 3 for 6)
                 || substring(uuid_send(gen_random_uuid()) from 7 for 10);
      -- versión 7 (nibble alto del byte 6 = 0111)
      v_bytes := set_byte(v_bytes, 6, (get_byte(v_bytes, 6) & 15) | 112);
      -- variante RFC 4122 (dos bits altos del byte 8 = 10)
      v_bytes := set_byte(v_bytes, 8, (get_byte(v_bytes, 8) & 63) | 128);
      RETURN encode(v_bytes, 'hex')::uuid;
    END;
    $f$;
  END IF;
END
$do$;

-- 0.2 · Trigger genérico para mantener updated_at.
CREATE OR REPLACE FUNCTION platform.set_updated_at() RETURNS trigger
LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := now();
  RETURN NEW;
END;
$$;

-- 0.3 · Crea (si no existe) una partición mensual [mes, mes+1) para una tabla
--        particionada por rango temporal. Nombre: <tabla>_YYYYMM.
CREATE OR REPLACE FUNCTION platform.create_month_partition(
  p_schema text, p_table text, p_month date
) RETURNS void
LANGUAGE plpgsql AS $$
DECLARE
  v_from date := date_trunc('month', p_month)::date;
  v_to   date := (date_trunc('month', p_month) + interval '1 month')::date;
  v_name text := format('%s_%s', p_table, to_char(v_from, 'YYYYMM'));
BEGIN
  EXECUTE format(
    'CREATE TABLE IF NOT EXISTS %I.%I PARTITION OF %I.%I FOR VALUES FROM (%L) TO (%L)',
    p_schema, v_name, p_schema, p_table, v_from, v_to
  );
END;
$$;


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

/*
-- ============================================================================
--  2 · DISPATCH — puente genérico + idempotencia + auditoría de eventos
-- ============================================================================

-- Log append-only de cada despacho (auditoría/depuración). Particionado.
CREATE TABLE dispatch.dispatch_events (
  id              uuid NOT NULL DEFAULT uuidv7(),
  tenant_id       uuid NOT NULL,                        -- correlación (sin FK: alto volumen)
  event_id        text NOT NULL,
  conversation_id text,
  tool_name       text NOT NULL,
  arguments       jsonb NOT NULL,
  status          text NOT NULL DEFAULT 'pending'
                    CHECK (status IN ('pending','confirmed','failed','timeout')),
  response        jsonb,
  latency_ms      integer,
  attempts        integer NOT NULL DEFAULT 0,
  created_at      timestamptz NOT NULL DEFAULT now(),
  completed_at    timestamptz,
  PRIMARY KEY (id, created_at)
) PARTITION BY RANGE (created_at);
CREATE INDEX idx_dispatch_tenant ON dispatch.dispatch_events (tenant_id, created_at DESC);
CREATE TABLE dispatch.dispatch_events_default PARTITION OF dispatch.dispatch_events DEFAULT;

-- Idempotencia REAL (unicidad global; sin particionar). Redis va delante.
CREATE TABLE dispatch.idempotency_keys (
  tenant_id  uuid NOT NULL,
  event_id   text NOT NULL,
  result     jsonb,
  created_at timestamptz NOT NULL DEFAULT now(),
  expires_at timestamptz NOT NULL,
  PRIMARY KEY (tenant_id, event_id)
);
CREATE INDEX idx_idempotency_expiry ON dispatch.idempotency_keys (expires_at);

-- ============================================================================
--  3 · BILLING — planes, suscripciones, cuotas, uso y facturación
-- ============================================================================

CREATE TABLE billing.plans (
  id                    uuid PRIMARY KEY DEFAULT uuidv7(),
  code                  text NOT NULL UNIQUE,          -- alpha, pro, enterprise, ...
  name                  text NOT NULL,
  visibility            text NOT NULL DEFAULT 'public'
                          CHECK (visibility IN ('public','private')),
  owner_tenant_id       uuid REFERENCES tenancy.tenants(id),  -- solo planes privados
  included_minutes      integer NOT NULL DEFAULT 0,
  included_dispatches   integer,
  max_agents            integer,
  max_concurrent_calls  integer,
  overage_cents_per_min integer NOT NULL DEFAULT 0,
  hard_cap              boolean NOT NULL DEFAULT false,  -- true = bloquea al agotar
  monthly_price_cents   integer NOT NULL DEFAULT 0,
  currency              text NOT NULL DEFAULT 'EUR',
  stripe_price_id       text,
  active                boolean NOT NULL DEFAULT true,
  created_at            timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT plan_visibility_owner_chk CHECK (
    (visibility = 'public'  AND owner_tenant_id IS NULL) OR
    (visibility = 'private' AND owner_tenant_id IS NOT NULL)
  )
);

CREATE TABLE billing.subscriptions (
  id                     uuid PRIMARY KEY DEFAULT uuidv7(),
  tenant_id              uuid NOT NULL REFERENCES tenancy.tenants(id) ON DELETE CASCADE,
  plan_id                uuid NOT NULL REFERENCES billing.plans(id),
  stripe_subscription_id text,
  overrides              jsonb NOT NULL DEFAULT '{}',   -- deltas negociados sobre el plan
  status                 text NOT NULL DEFAULT 'active'
                           CHECK (status IN ('active','past_due','suspended','canceled')),
  current_period_start   timestamptz NOT NULL,
  current_period_end     timestamptz NOT NULL,
  created_at             timestamptz NOT NULL DEFAULT now(),
  updated_at             timestamptz NOT NULL DEFAULT now()
);
-- Máximo una suscripción activa por tenant.
CREATE UNIQUE INDEX uq_active_subscription ON billing.subscriptions (tenant_id)
  WHERE status = 'active';
CREATE TRIGGER trg_subscriptions_updated BEFORE UPDATE ON billing.subscriptions
  FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();

-- Contador de consumo del periodo (fuente para la comprobación de cuota).
CREATE TABLE billing.quota_counters (
  tenant_id       uuid NOT NULL,
  period          text NOT NULL,                        -- YYYY-MM
  minutes_used    numeric(12,2) NOT NULL DEFAULT 0,
  dispatches_used integer NOT NULL DEFAULT 0,
  updated_at      timestamptz NOT NULL DEFAULT now(),
  PRIMARY KEY (tenant_id, period)
);

-- Cold path: hecho crudo de la llamada (CONTIENE PII). Particionado.
CREATE TABLE billing.call_records (
  id              uuid NOT NULL DEFAULT uuidv7(),
  tenant_id       uuid NOT NULL,
  conversation_id text NOT NULL,                        -- id de ElevenLabs (idempotencia post-call)
  agent_id        uuid,
  caller_e164     text,                                 -- PII → borrable (erasure)
  direction       text NOT NULL DEFAULT 'inbound'
                    CHECK (direction IN ('inbound','outbound')),
  started_at      timestamptz NOT NULL,
  ended_at        timestamptz NOT NULL,
  duration_sec    integer NOT NULL,
  el_cost_cents   integer,                              -- COGS ElevenLabs → margen real
  llm_tokens      integer,
  transcript_ref  text,                                 -- puntero a object storage (PII)
  pii_scrubbed_at timestamptz,                          -- marca de borrado GDPR
  created_at      timestamptz NOT NULL DEFAULT now(),
  PRIMARY KEY (id, started_at),
  UNIQUE (conversation_id, started_at),
  CONSTRAINT call_time_chk CHECK (ended_at >= started_at),
  CONSTRAINT call_duration_chk CHECK (duration_sec >= 0)
) PARTITION BY RANGE (started_at);
CREATE INDEX idx_calls_tenant ON billing.call_records (tenant_id, started_at DESC);
CREATE TABLE billing.call_records_default PARTITION OF billing.call_records DEFAULT;

-- Ledger económico: inmutable y SIN PII (sobrevive al derecho al olvido).
CREATE TABLE billing.usage_ledger (
  id             uuid PRIMARY KEY DEFAULT uuidv7(),
  tenant_id      uuid NOT NULL,
  call_record_id uuid NOT NULL,                         -- ref lógica (sin FK: partición)
  period         text NOT NULL,                         -- YYYY-MM
  billable_sec   integer NOT NULL,
  amount_cents   integer NOT NULL,
  is_overage     boolean NOT NULL DEFAULT false,
  currency       text NOT NULL DEFAULT 'EUR',
  reported       boolean NOT NULL DEFAULT false,
  created_at     timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX idx_ledger_unreported ON billing.usage_ledger (tenant_id, period)
  WHERE reported = false;
-- Política de borrado (referencias lógicas sin FK por volumen/partición):
--  · call_records y dispatch_events se retiran por DROP de partición antigua.
--  · usage_ledger es inmutable y NUNCA se borra (dato económico sin PII).
--  · El scrubbing GDPR actúa sobre call_records (PII), no sobre el ledger.

CREATE TABLE billing.invoices (
  id                uuid PRIMARY KEY DEFAULT uuidv7(),
  tenant_id         uuid NOT NULL REFERENCES tenancy.tenants(id),
  period            text NOT NULL,                       -- YYYY-MM
  stripe_invoice_id text,
  amount_cents      integer NOT NULL,
  currency          text NOT NULL DEFAULT 'EUR',
  status            text NOT NULL DEFAULT 'draft'
                      CHECK (status IN ('draft','open','paid','void','uncollectible')),
  created_at        timestamptz NOT NULL DEFAULT now(),
  updated_at        timestamptz NOT NULL DEFAULT now(),
  UNIQUE (tenant_id, period)
);
CREATE TRIGGER trg_invoices_updated BEFORE UPDATE ON billing.invoices
  FOR EACH ROW EXECUTE FUNCTION platform.set_updated_at();

-- Titularidad efectiva = plan + overrides. La resolución de cuota lee de aquí.
CREATE VIEW billing.effective_entitlements AS
SELECT
  s.tenant_id,
  s.plan_id,
  p.code AS plan_code,
  COALESCE((s.overrides->>'included_minutes')::int,      p.included_minutes)      AS included_minutes,
  COALESCE((s.overrides->>'included_dispatches')::int,   p.included_dispatches)   AS included_dispatches,
  COALESCE((s.overrides->>'max_agents')::int,            p.max_agents)            AS max_agents,
  COALESCE((s.overrides->>'max_concurrent_calls')::int,  p.max_concurrent_calls)  AS max_concurrent_calls,
  COALESCE((s.overrides->>'overage_cents_per_min')::int, p.overage_cents_per_min) AS overage_cents_per_min,
  COALESCE((s.overrides->>'hard_cap')::bool,             p.hard_cap)              AS hard_cap,
  s.current_period_start,
  s.current_period_end
FROM billing.subscriptions s
JOIN billing.plans p ON p.id = s.plan_id
WHERE s.status = 'active';

-- ============================================================================
--  4 · PLATFORM — credenciales, fiabilidad, auditoría y cumplimiento
-- ============================================================================

-- Credenciales máquina-a-máquina con ámbito. El Core autentica APPS, no personas.
CREATE TABLE platform.api_credentials (
  id           uuid PRIMARY KEY DEFAULT uuidv7(),
  name         text NOT NULL,
  scope        text NOT NULL CHECK (scope IN ('platform','tenant','reseller')),
  tenant_id    uuid REFERENCES tenancy.tenants(id) ON DELETE CASCADE,  -- NULL si platform
  key_prefix   text NOT NULL,                           -- primeros chars (identificación)
  key_hash     text NOT NULL,                           -- hash (argon2/bcrypt); NUNCA la key
  permissions  text[] NOT NULL DEFAULT '{}',            -- tenant.write, plan.assign, config.write...
  status       text NOT NULL DEFAULT 'active'
                 CHECK (status IN ('active','revoked')),
  expires_at   timestamptz,
  last_used_at timestamptz,
  created_at   timestamptz NOT NULL DEFAULT now(),
  CONSTRAINT scope_tenant_chk CHECK (
    (scope = 'platform'              AND tenant_id IS NULL) OR
    (scope IN ('tenant','reseller')  AND tenant_id IS NOT NULL)
  )
);
CREATE UNIQUE INDEX uq_api_key_prefix ON platform.api_credentials (key_prefix);
CREATE INDEX idx_api_cred_tenant ON platform.api_credentials (tenant_id) WHERE status = 'active';

-- Registro crudo de webhooks entrantes (dedup + dead-letter). No se pierde nada.
CREATE TABLE platform.webhook_deliveries (
  id           uuid PRIMARY KEY DEFAULT uuidv7(),
  source       text NOT NULL,                           -- elevenlabs
  kind         text NOT NULL
                 CHECK (kind IN ('personalization','dispatch','post_call')),
  external_id  text,                                     -- conversation_id / delivery id
  payload      jsonb NOT NULL,
  signature    text,
  status       text NOT NULL DEFAULT 'received'
                 CHECK (status IN ('received','processed','failed','dead_letter')),
  attempts     integer NOT NULL DEFAULT 0,
  last_error   text,
  received_at  timestamptz NOT NULL DEFAULT now(),
  processed_at timestamptz,
  UNIQUE (source, kind, external_id)
);
CREATE INDEX idx_webhook_pending ON platform.webhook_deliveries (status)
  WHERE status IN ('received','failed');

-- Transactional outbox: entrega interna fiable (p.ej. post-call → billing).
CREATE TABLE platform.outbox (
  id           uuid PRIMARY KEY DEFAULT uuidv7(),
  aggregate    text NOT NULL,                            -- call_record, subscription, ...
  event_type   text NOT NULL,
  payload      jsonb NOT NULL,
  status       text NOT NULL DEFAULT 'pending'
                 CHECK (status IN ('pending','processed','failed')),
  created_at   timestamptz NOT NULL DEFAULT now(),
  processed_at timestamptz
);
CREATE INDEX idx_outbox_pending ON platform.outbox (created_at) WHERE status = 'pending';

-- Auditoría de acciones de administración (identidad opaca del actor).
CREATE TABLE platform.audit_log (
  id                   uuid PRIMARY KEY DEFAULT uuidv7(),
  tenant_id            uuid,
  actor_id             text NOT NULL,                    -- opaco (lo pasa la app externa)
  actor_credential_id  uuid REFERENCES platform.api_credentials(id),
  action               text NOT NULL,                    -- agent.updated, plan.assigned, ...
  target               text NOT NULL,
  metadata             jsonb NOT NULL DEFAULT '{}',
  created_at           timestamptz NOT NULL DEFAULT now()
);
CREATE INDEX idx_audit_tenant ON platform.audit_log (tenant_id, created_at DESC);

-- Solicitudes de borrado (GDPR / derecho al olvido).
CREATE TABLE platform.erasure_requests (
  id           uuid PRIMARY KEY DEFAULT uuidv7(),
  tenant_id    uuid NOT NULL REFERENCES tenancy.tenants(id),
  subject_ref  text NOT NULL,                            -- p.ej. e164 del llamante
  status       text NOT NULL DEFAULT 'pending'
                 CHECK (status IN ('pending','completed','rejected')),
  requested_at timestamptz NOT NULL DEFAULT now(),
  completed_at timestamptz
);

-- ============================================================================
--  5 · ROW-LEVEL SECURITY (aislamiento estricto en tablas tenant-scoped)
-- ----------------------------------------------------------------------------
--  La app fija por transacción:  SET LOCAL app.current_tenant = '<uuid>';
--  Una conexión de ámbito 'platform' usa un rol con BYPASSRLS.
-- ============================================================================

DO $rls$
DECLARE
  t text;
  tables text[] := ARRAY[
    'tenancy.agents','tenancy.agent_versions','tenancy.phone_numbers',
    'tenancy.tools','tenancy.tool_secrets',
    'billing.subscriptions','billing.quota_counters','billing.call_records',
    'billing.usage_ledger','billing.invoices'
  ];
BEGIN
  FOREACH t IN ARRAY tables LOOP
    EXECUTE format('ALTER TABLE %s ENABLE ROW LEVEL SECURITY', t);
    EXECUTE format($p$
      CREATE POLICY tenant_isolation ON %s
      USING (tenant_id = NULLIF(current_setting('app.current_tenant', true), '')::uuid)
    $p$, t);
  END LOOP;
END
$rls$;

-- ============================================================================
--  6 · PARTICIONES INICIALES (mes anterior, actual y dos siguientes)
-- ----------------------------------------------------------------------------
--  En producción, automatizar con pg_cron/pg_partman para crear el mes futuro.
-- ============================================================================

DO $part$
DECLARE
  m date;
BEGIN
  FOR m IN
    SELECT generate_series(
      date_trunc('month', now()) - interval '1 month',
      date_trunc('month', now()) + interval '2 month',
      interval '1 month'
    )::date
  LOOP
    PERFORM platform.create_month_partition('dispatch','dispatch_events', m);
    PERFORM platform.create_month_partition('billing','call_records', m);
  END LOOP;
END
$part$;

-- ============================================================================
--  FIN DEL ESQUEMA
-- ============================================================================*/