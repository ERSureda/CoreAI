-- ========================================================
--  V1
--  Module: audit
-- ========================================================

CREATE SCHEMA IF NOT EXISTS audit;

CREATE TABLE audit.audit_log (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	occurred_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	aggregate_type TEXT NOT NULL,
	aggregate_id UUID NOT NULL,
	action TEXT NOT NULL,
	actor_type common.actor_type NOT NULL,
	actor_id UUID,
	source TEXT NOT NULL,
	event_id UUID,
	before JSONB,
	after JSONB,
	ip INET,
	PRIMARY KEY (id, occurred_at)
) PARTITION BY RANGE (occurred_at);

CREATE INDEX idx_audit_aggregate ON audit.audit_log(aggregate_type, aggregate_id, occurred_at);
CREATE INDEX idx_audit_actor ON audit.audit_log(actor_type, actor_id, occurred_at);
CREATE INDEX idx_audit_brin ON audit.audit_log USING brin(occurred_at);


CREATE TABLE audit.audit_log_default PARTITION OF audit.audit_log DEFAULT;


CREATE TABLE audit.outbox_events (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	aggregate_type TEXT NOT NULL,
	aggregate_id UUID NOT NULL,
	event_type TEXT NOT NULL,
	payload JSONB NOT NULL,
	message_group TEXT NOT NULL,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	published_at TIMESTAMPTZ,
	attempts SMALLINT NOT NULL DEFAULT 0,
	next_retry_at TIMESTAMPTZ
);

CREATE INDEX idx_outbox_pending ON audit.outbox_events(created_at) WHERE published_at IS NULL;


CREATE TABLE audit.processed_messages (
	consumer TEXT NOT NULL,
	message_id TEXT NOT NULL,
	processed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	PRIMARY KEY (consumer, message_id)
);


DO $$
BEGIN
	IF EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'app_role') THEN
		REVOKE UPDATE, DELETE ON booking.trip_status_history FROM app_role;
		REVOKE UPDATE, DELETE ON billing.wallet_ledger FROM app_role;
		REVOKE UPDATE, DELETE ON audit.audit_log FROM app_role;
	END IF;
END $$;
