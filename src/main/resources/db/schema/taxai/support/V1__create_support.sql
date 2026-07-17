-- ========================================================
--  V1
--  Module: support
--  Goal: Manage operational incidents and lost item recovery:
--      - Track support tickets linked to specific trips or systemic failures
--      - Provide an audit trail for incident resolution and accountability
--      - Manage the lifecycle of lost items from report to return
-- ========================================================

CREATE SCHEMA IF NOT EXISTS support;

CREATE TYPE support.incident_type AS ENUM ('ACCIDENT','COMPLAINT','LOST_ITEM','VEHICLE_BREAKDOWN','GPS_SIGNAL_LOST','PAYMENT_ISSUE','SAFETY','DELAY','APP_FAILURE','OTHER');
CREATE TYPE support.incident_status AS ENUM ('OPEN','IN_REVIEW','RESOLVED','CLOSED');
CREATE TYPE support.lost_item_status AS ENUM ('REPORTED','FOUND','RETURNED','UNCLAIMED','DONATED');
CREATE TYPE support.actor_type AS ENUM ('PASSENGER','DRIVER','OPERATOR','SYSTEM','AI_AGENT');

CREATE TABLE support.incidents (
	id UUID PRIMARY KEY,
	trip_id UUID,
	type support.incident_type NOT NULL,
	status support.incident_status NOT NULL DEFAULT 'OPEN',
	priority SMALLINT NOT NULL DEFAULT 3 CHECK (priority BETWEEN 1 AND 5),
	reported_by common.actor_type NOT NULL,
	reporter_id UUID,
	assignee_id UUID,
	title TEXT NOT NULL,
	description TEXT,
	resolution TEXT,
	source_event_id UUID,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	resolved_at TIMESTAMPTZ,
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_incidents_trip ON support.incidents(trip_id);
CREATE INDEX idx_incidents_open ON support.incidents(priority, created_at) WHERE status IN ('OPEN','IN_REVIEW');

CREATE TRIGGER trg_incidents_updated BEFORE UPDATE ON support.incidents
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE support.lost_items (
	id UUID PRIMARY KEY,
	trip_id UUID NOT NULL,
	incident_id UUID REFERENCES support.incidents(id),
	description TEXT NOT NULL,
	status support.lost_item_status NOT NULL DEFAULT 'REPORTED',
	storage_location TEXT,
	contact_phone TEXT,
	found_at TIMESTAMPTZ,
	returned_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_lost_items_trip ON support.lost_items(trip_id);
CREATE INDEX idx_lost_items_open ON support.lost_items(status) WHERE status IN ('REPORTED','FOUND');
