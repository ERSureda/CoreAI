-- ========================================================
--  V1
--  Module: audit
--  Goal: Answer four key questions for any major business event:
--      - What changed (aggregate_type)
--      - Who changed it (actor_type)
--      - When it occurred (occurred_at)
--      - Where is the full detail (event_id)
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
	event_id UUID
	PRIMARY KEY (id, occurred_at)
) PARTITION BY RANGE (occurred_at);

CREATE INDEX idx_audit_aggregate ON audit.audit_log(aggregate_type, aggregate_id, occurred_at);
CREATE INDEX idx_audit_actor ON audit.audit_log(actor_type, actor_id, occurred_at);
CREATE INDEX idx_audit_brin ON audit.audit_log USING brin(occurred_at);


CREATE TABLE audit.audit_log_default PARTITION OF audit.audit_log DEFAULT;

DO $$
BEGIN
	IF EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'app_role') THEN
		REVOKE UPDATE, DELETE ON trip.trip_status_history FROM app_role;
		REVOKE UPDATE, DELETE ON pricing.wallet_ledger FROM app_role;
		REVOKE UPDATE, DELETE ON audit.audit_log FROM app_role;
	END IF;
END $$;
