-- ======================================================== (HA DE ESTAR EN TODAS LAS BDD)
--  V1
--  Module: messaging
--  Goal: Ensure reliable event publishing and exactly-once processing across distributed systems:
--      - Eliminate dual-write issues by storing outgoing events within the local aggregate transaction
--      - Provide a transactional outbox queue for safe asynchronous dispatch to message brokers
--      - Prevent duplicate event execution via idempotent consumer tracking
-- ========================================================

CREATE TABLE outbox_events (
	id UUID PRIMARY KEY,
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

CREATE INDEX idx_outbox_pending ON outbox_events(created_at) WHERE published_at IS NULL;


CREATE TABLE processed_messages (
	consumer TEXT NOT NULL,
	message_id TEXT NOT NULL,
	processed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	PRIMARY KEY (consumer, message_id)
);
