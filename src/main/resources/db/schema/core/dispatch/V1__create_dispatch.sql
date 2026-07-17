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
