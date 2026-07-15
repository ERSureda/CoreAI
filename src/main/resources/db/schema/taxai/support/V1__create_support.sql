-- ========================================================
--  V1
--  Module: support
-- ========================================================

CREATE SCHEMA IF NOT EXISTS support;

CREATE TYPE support.incident_type AS ENUM ('ACCIDENT','COMPLAINT','LOST_ITEM','VEHICLE_BREAKDOWN','GPS_SIGNAL_LOST','PAYMENT_ISSUE','SAFETY','DELAY','APP_FAILURE','OTHER');
CREATE TYPE support.incident_status AS ENUM ('OPEN','IN_REVIEW','RESOLVED','CLOSED');
CREATE TYPE support.lost_item_status AS ENUM ('REPORTED','FOUND','RETURNED','UNCLAIMED','DONATED');
CREATE TYPE support.notification_channel AS ENUM ('SMS','PUSH','WHATSAPP','EMAIL','VOICE_CALL');

CREATE TABLE support.incidents (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	trip_id UUID REFERENCES booking.trips(id),
	type support.incident_type NOT NULL,
	status support.incident_status NOT NULL DEFAULT 'OPEN',
	priority SMALLINT NOT NULL DEFAULT 3 CHECK (priority BETWEEN 1 AND 5),
	reported_by common.actor_type NOT NULL,
	reporter_id UUID,
	assignee_id UUID REFERENCES registry.operators(id),
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
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	trip_id UUID NOT NULL REFERENCES booking.trips(id),
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


CREATE TABLE support.notification_templates (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	code TEXT NOT NULL,
	channel support.notification_channel NOT NULL,
	language CHAR(2) NOT NULL DEFAULT 'es',
	subject TEXT,
	body TEXT NOT NULL,
	version SMALLINT NOT NULL DEFAULT 1,
	active BOOLEAN NOT NULL DEFAULT true,
	UNIQUE (code, channel, language, version)
);


CREATE TABLE support.notification_preferences (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	owner_type common.actor_type NOT NULL,
	owner_id UUID NOT NULL,
	channel support.notification_channel NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT true,
	quiet_from TIME,
	quiet_to TIME,
	UNIQUE (owner_type, owner_id, channel)
);
