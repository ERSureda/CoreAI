-- ========================================================
--  V1
--  Module: common
-- ========================================================

CREATE SCHEMA IF NOT EXISTS common;

CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS btree_gist;
CREATE EXTENSION IF NOT EXISTS postgis;
CREATE EXTENSION IF NOT EXISTS pg_stat_statements;

CREATE OR REPLACE FUNCTION common.set_updated_at() RETURNS trigger AS $$
BEGIN
	NEW.updated_at := now();
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ======================================================== (NO FER CAS)
--  V1
--  Module: geo
--  Goal: Manage spatial data, geographical boundaries, and location caching:
--      - Cache resolved addresses and coordinates to minimize external API costs
--      - Define and evaluate polygons for service areas, restricted zones, and pricing
--      - Utilize geohashing for spatial grouping and routing optimization
-- ========================================================

CREATE SCHEMA IF NOT EXISTS geo;

CREATE TYPE geo.zone_kind AS ENUM ('SERVICE_AREA','TARIFF_ZONE','AIRPORT','STATION','RESTRICTED');

CREATE TABLE geo.locations (
	id UUID PRIMARY KEY,
	place_id TEXT UNIQUE,
	provider TEXT NOT NULL DEFAULT 'GOOGLE',
	formatted_address TEXT NOT NULL,
	street TEXT,
	street_number TEXT,
	city TEXT,
	postal_code TEXT,
	region TEXT,
	country CHAR(2) NOT NULL DEFAULT 'ES',
	lat NUMERIC(9,6) NOT NULL,
	lng NUMERIC(9,6) NOT NULL,
	geohash7 CHAR(7) NOT NULL,
	location_quality TEXT,
	refreshed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_locations_geohash ON geo.locations(geohash7);


CREATE TABLE geo.zones (
	id UUID PRIMARY KEY,
	name TEXT NOT NULL,
	kind geo.zone_kind NOT NULL,
	geom GEOGRAPHY(MultiPolygon, 4326) NOT NULL,
	active BOOLEAN NOT NULL DEFAULT true,
	extra JSONB NOT NULL DEFAULT '{}'::JSONB
);

CREATE INDEX idx_zones_geom ON geo.zones USING gist(geom);

-- ======================================================== (NO UTILIZAR)
--  V1
--  Module: iam (Identity and Access Management)
--  Goal: Centralize authentication and core user identity for all systems:
--      - Provide a single source of truth for user credentials and login states
--      - Decouple authentication (who you are) from domain authorization (what you do)
--      - Manage session security, password policies, and account locking
-- ========================================================

CREATE SCHEMA IF NOT EXISTS iam;

CREATE TYPE iam.user_status AS ENUM ('PENDING_VERIFICATION', 'ACTIVE', 'LOCKED', 'SUSPENDED');

CREATE TABLE iam.users (
	id UUID PRIMARY KEY,
	email TEXT UNIQUE,
	phone TEXT UNIQUE CHECK (phone ~ '^\+[1-9][0-9]{6,14}$'),
	status iam.user_status NOT NULL DEFAULT 'PENDING_VERIFICATION',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CHECK (email IS NOT NULL OR phone IS NOT NULL)
);

CREATE TRIGGER trg_iam_users_updated BEFORE UPDATE ON iam.users
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE iam.credentials (
	user_id UUID PRIMARY KEY REFERENCES iam.users(id) ON DELETE CASCADE,
	password_hash TEXT,
	failed_login_count SMALLINT NOT NULL DEFAULT 0,
	locked_until TIMESTAMPTZ,
	last_login_at TIMESTAMPTZ,
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER trg_iam_credentials_updated BEFORE UPDATE ON iam.credentials
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE iam.refresh_tokens (
	id UUID PRIMARY KEY,
	user_id UUID NOT NULL REFERENCES iam.users(id) ON DELETE CASCADE,
	token_hash TEXT NOT NULL UNIQUE,
	expires_at TIMESTAMPTZ NOT NULL,
	revoked_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	created_ip TEXT,
	user_agent TEXT
);

CREATE INDEX ix_iam_refresh_tokens_user ON iam.refresh_tokens(user_id) WHERE revoked_at IS NULL;

-- ========================================================
--  V1
--  Module: fleet (B2B Multi-Tenant Core)
--  Goal: Manage physical assets and staff for each taxi company securely:
--      - Enforce strict data isolation between SaaS tenants (taxi fleets)
--      - Link operators and drivers to IAM securely without mixing concerns
--      - Prevent double-booking of physical vehicles using temporal constraints
-- ========================================================

CREATE SCHEMA IF NOT EXISTS fleet;

CREATE TYPE fleet.vehicle_type AS ENUM ('SEDAN', 'ESTATE', 'MINIVAN_6', 'VAN_9', 'LUXURY', 'EV', 'WHEELCHAIR_ACCESSIBLE');
CREATE TYPE fleet.vehicle_status AS ENUM ('ACTIVE', 'MAINTENANCE', 'INACTIVE', 'RETIRED');
CREATE TYPE fleet.driver_admin_status AS ENUM ('ONBOARDING', 'ACTIVE', 'SUSPENDED', 'INACTIVE');

CREATE TABLE fleet.tenants (
	id UUID PRIMARY KEY,
	name TEXT NOT NULL,
	tax_id TEXT NOT NULL UNIQUE,
	is_active BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);


CREATE TABLE fleet.operators (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES fleet.tenants(id) ON DELETE CASCADE,
	user_id UUID NOT NULL UNIQUE,
	full_name TEXT NOT NULL,
	role TEXT NOT NULL DEFAULT 'AGENT',
	is_active BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX ix_operators_tenant ON fleet.operators(tenant_id);


CREATE TABLE fleet.vehicles (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES fleet.tenants(id) ON DELETE CASCADE,
	plate TEXT NOT NULL,
	make TEXT NOT NULL,
	model TEXT NOT NULL,
	type fleet.vehicle_type NOT NULL,
	status fleet.vehicle_status NOT NULL DEFAULT 'ACTIVE',
	passenger_seats SMALLINT NOT NULL CHECK (passenger_seats BETWEEN 1 AND 16),
	wheelchair_accessible BOOLEAN NOT NULL DEFAULT false,
	allows_pets BOOLEAN NOT NULL DEFAULT false,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (tenant_id, plate)
);

CREATE INDEX ix_vehicles_matching ON fleet.vehicles(tenant_id, type, status) WHERE status = 'ACTIVE';


CREATE TABLE fleet.drivers (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES fleet.tenants(id) ON DELETE CASCADE,
	user_id UUID UNIQUE,
	employee_code TEXT NOT NULL,
	full_name TEXT NOT NULL,
	phone TEXT NOT NULL CHECK (phone ~ '^\+[1-9][0-9]{6,14}$'),
	admin_status fleet.driver_admin_status NOT NULL DEFAULT 'ONBOARDING',
	default_vehicle_id UUID REFERENCES fleet.vehicles(id),
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (tenant_id, employee_code),
	UNIQUE (tenant_id, phone)
);

CREATE INDEX ix_drivers_tenant_status ON fleet.drivers(tenant_id, admin_status);


CREATE TABLE fleet.driver_vehicle_assignments (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL REFERENCES fleet.tenants(id) ON DELETE CASCADE,
	driver_id UUID NOT NULL REFERENCES fleet.drivers(id),
	vehicle_id UUID NOT NULL REFERENCES fleet.vehicles(id),
	valid_from TIMESTAMPTZ NOT NULL DEFAULT now(),
	valid_to TIMESTAMPTZ,
	created_by UUID REFERENCES fleet.operators(id),
	EXCLUDE USING gist (vehicle_id WITH =, TSTZRANGE(valid_from, valid_to) WITH &&)
);

CREATE UNIQUE INDEX uq_dva_driver_current ON fleet.driver_vehicle_assignments(driver_id) WHERE valid_to IS NULL;
CREATE UNIQUE INDEX uq_dva_vehicle_current ON fleet.driver_vehicle_assignments(vehicle_id) WHERE valid_to IS NULL;

-- ========================================================
--  V1
--  Module: booking (Commercial Intent)
--  Goal: Capture and manage the commercial intent of a passenger requesting a ride:
--      - Translate AI voice agent conversations into structured booking data
--      - Enforce strict data isolation per taxi fleet (tenant_id)
--      - Manage immediate, scheduled, and recurring ride requirements
--      - Store specific passenger needs (luggage, pets, accessibility) for dispatch matching
-- ========================================================

CREATE SCHEMA IF NOT EXISTS booking;

CREATE TYPE booking.channel AS ENUM ('AI_VOICE', 'PHONE_OPERATOR', 'APP', 'WEB', 'B2B_API');
CREATE TYPE booking.type AS ENUM ('IMMEDIATE', 'SCHEDULED', 'RECURRING');
CREATE TYPE booking.status AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED', 'FULFILLED');
CREATE TYPE booking.vehicle_type AS ENUM ('SEDAN', 'ESTATE', 'MINIVAN_6', 'VAN_9', 'LUXURY', 'EV', 'WHEELCHAIR_ACCESSIBLE');
CREATE TYPE booking.actor_type AS ENUM ('PASSENGER', 'OPERATOR', 'SYSTEM', 'AI_AGENT');
CREATE TYPE booking.luggage_type AS ENUM ('CABIN', 'LARGE', 'EXTRA_LARGE', 'SPORT_EQUIPMENT', 'WHEELCHAIR_FOLDABLE');
CREATE TYPE booking.pet_type AS ENUM ('SMALL_IN_CARRIER', 'DOG_MEDIUM', 'DOG_LARGE', 'GUIDE_DOG', 'OTHER');

CREATE TABLE booking.bookings (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL,
	locator CHAR(8) NOT NULL UNIQUE,
	passenger_id UUID NOT NULL,
	channel booking.channel NOT NULL,
	type booking.type NOT NULL,
	status booking.status NOT NULL DEFAULT 'CONFIRMED',
	scheduled_pickup_at TIMESTAMPTZ,
	pickup_timezone TEXT NOT NULL DEFAULT 'Europe/Madrid',
	recurrence_rule TEXT,
	recurrence_until DATE,
	passenger_count SMALLINT NOT NULL DEFAULT 1 CHECK (passenger_count BETWEEN 1 AND 16),
	vehicle_type_required booking.vehicle_type,
	needs_child_seat BOOLEAN NOT NULL DEFAULT false,
	wheelchair_required BOOLEAN NOT NULL DEFAULT false,
	notes TEXT,
	source_conversation_id TEXT,
	created_by_actor booking.actor_type NOT NULL,
	created_by_id UUID,
	version INT NOT NULL DEFAULT 0,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CHECK (type <> 'SCHEDULED' OR scheduled_pickup_at IS NOT NULL),
	CHECK (type <> 'RECURRING' OR recurrence_rule IS NOT NULL)
);

CREATE INDEX ix_bookings_tenant_date ON booking.bookings(tenant_id, created_at DESC);
CREATE INDEX ix_bookings_passenger ON booking.bookings(passenger_id, created_at DESC);
CREATE INDEX ix_bookings_recurring ON booking.bookings(recurrence_until) WHERE type = 'RECURRING' AND status = 'CONFIRMED';
CREATE INDEX ix_bookings_conversation ON booking.bookings(source_conversation_id) WHERE source_conversation_id IS NOT NULL;

CREATE TRIGGER trg_bookings_updated BEFORE UPDATE ON booking.bookings
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE booking.booking_luggage (
	id UUID PRIMARY KEY,
	booking_id UUID NOT NULL REFERENCES booking.bookings(id) ON DELETE CASCADE,
	type booking.luggage_type NOT NULL,
	quantity SMALLINT NOT NULL DEFAULT 1 CHECK (quantity > 0),
	notes TEXT
);

CREATE INDEX ix_booking_luggage ON booking.booking_luggage(booking_id);


CREATE TABLE booking.booking_pets (
	id UUID PRIMARY KEY,
	booking_id UUID NOT NULL REFERENCES booking.bookings(id) ON DELETE CASCADE,
	type booking.pet_type NOT NULL,
	quantity SMALLINT NOT NULL DEFAULT 1,
	in_carrier BOOLEAN NOT NULL DEFAULT false,
	notes TEXT
);

CREATE INDEX ix_booking_pets ON booking.booking_pets(booking_id);

-- ========================================================
--  V1
--  Module: trip (Trip Execution)
--  Goal: Manage the operational lifecycle of a taxi ride from dispatch to completion:
--      - Provide a robust state machine for the trip with optimistic locking
--      - Maintain an immutable, partitioned audit trail of all state changes
--      - Isolate dependencies using soft links to allow microservice scaling
-- ========================================================

CREATE SCHEMA IF NOT EXISTS trip;

CREATE TYPE trip.trip_status AS ENUM ('REQUESTED', 'SCHEDULED', 'SEARCHING', 'OFFERED', 'ASSIGNED', 'ACCEPTED', 'ARRIVING', 'WAITING', 'ON_BOARD', 'COMPLETED', 'CANCELLED', 'FAILED');
CREATE TYPE trip.actor_type AS ENUM ('PASSENGER', 'DRIVER', 'OPERATOR', 'SYSTEM', 'AI_AGENT');
CREATE TYPE trip.cancel_reason AS ENUM ('PASSENGER_NO_SHOW', 'DRIVER_NO_SHOW', 'PASSENGER_REQUESTED', 'DRIVER_REQUESTED', 'OPERATOR_REQUESTED', 'SYSTEM_TIMEOUT', 'VEHICLE_BREAKDOWN');
CREATE TYPE trip.stop_type AS ENUM ('PICKUP', 'DROPOFF', 'WAYPOINT');
CREATE TYPE trip.stop_status AS ENUM ('PENDING', 'ARRIVED', 'COMPLETED', 'SKIPPED');
CREATE TYPE trip.route_reason AS ENUM ('INITIAL_ESTIMATE', 'DRIVER_REROUTE', 'TRAFFIC_AVOIDANCE');

CREATE TABLE trip.trips (
	id UUID PRIMARY KEY,
	tenant_id UUID NOT NULL,
	booking_id UUID NOT NULL,
	passenger_id UUID NOT NULL,
	driver_id UUID,
	vehicle_id UUID,
	status trip.trip_status NOT NULL DEFAULT 'REQUESTED',
	replaces_trip_id UUID REFERENCES trip.trips(id),
	scheduled_pickup_at TIMESTAMPTZ,
	search_started_at TIMESTAMPTZ,
	search_expires_at TIMESTAMPTZ,
	assigned_at TIMESTAMPTZ,
	accepted_at TIMESTAMPTZ,
	arriving_started_at TIMESTAMPTZ,
	waiting_started_at TIMESTAMPTZ,
	wait_deadline_at TIMESTAMPTZ,
	boarded_at TIMESTAMPTZ,
	completed_at TIMESTAMPTZ,
	cancelled_at TIMESTAMPTZ,
	cancelled_by trip.actor_type,
	cancel_reason trip.cancel_reason,
	cancel_note TEXT,
	estimated_distance_m INT,
	estimated_duration_s INT,
	actual_distance_m INT,
	actual_duration_s INT,
	active_route_version SMALLINT NOT NULL DEFAULT 0,
	version INT NOT NULL DEFAULT 0,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CHECK (status <> 'CANCELLED' OR cancelled_by IS NOT NULL)
);

CREATE INDEX ix_trips_tenant ON trip.trips(tenant_id, created_at DESC);
CREATE INDEX ix_trips_driver ON trip.trips(driver_id, created_at DESC) WHERE driver_id IS NOT NULL;
CREATE UNIQUE INDEX uq_driver_active_trip ON trip.trips(driver_id) WHERE status IN ('ASSIGNED', 'ACCEPTED', 'ARRIVING', 'WAITING', 'ON_BOARD');
CREATE INDEX ix_trips_active_ops ON trip.trips(status, updated_at DESC) WHERE status IN ('SEARCHING', 'OFFERED', 'ASSIGNED', 'ACCEPTED', 'ARRIVING', 'WAITING', 'ON_BOARD');

CREATE TRIGGER trg_trips_updated BEFORE UPDATE ON trip.trips
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();
CREATE TRIGGER trg_trip_status_history AFTER UPDATE OF status ON trip.trips
	FOR EACH ROW EXECUTE FUNCTION trip.log_trip_status_change();


CREATE TABLE trip.trip_stops (
	id UUID PRIMARY KEY,
	trip_id UUID NOT NULL REFERENCES trip.trips(id) ON DELETE CASCADE,
	seq SMALLINT NOT NULL,
	type trip.stop_type NOT NULL,
	status trip.stop_status NOT NULL DEFAULT 'PENDING',
	location_id UUID,
	address_snapshot JSONB NOT NULL,
	contact_name TEXT,
	contact_phone TEXT,
	eta_at TIMESTAMPTZ,
	arrived_at TIMESTAMPTZ,
	departed_at TIMESTAMPTZ,
	notes TEXT,
	UNIQUE (trip_id, seq)
);

CREATE INDEX ix_trip_stops_trip ON trip.trip_stops(trip_id, seq);


CREATE TABLE trip.trip_routes (
	id UUID PRIMARY KEY,
	trip_id UUID NOT NULL REFERENCES trip.trips(id) ON DELETE CASCADE,
	route_version SMALLINT NOT NULL,
	reason trip.route_reason NOT NULL,
	provider TEXT NOT NULL DEFAULT 'GOOGLE_ROUTES',
	encoded_polyline TEXT NOT NULL,
	distance_m INT NOT NULL,
	duration_s INT NOT NULL,
	duration_traffic_s INT,
	waypoints JSONB,
	computed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (trip_id, route_version)
);


CREATE TABLE trip.trip_status_history (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	trip_id UUID NOT NULL,
	from_status trip.trip_status,
	to_status trip.trip_status NOT NULL,
	actor_type trip.actor_type NOT NULL,
	actor_id UUID,
	reason TEXT,
	event_id UUID,
	metadata JSONB NOT NULL DEFAULT '{}'::JSONB,
	occurred_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	PRIMARY KEY (id, occurred_at)
) PARTITION BY RANGE (occurred_at);

CREATE INDEX ix_tsh_trip ON trip.trip_status_history(trip_id, occurred_at);


CREATE TABLE trip.trip_status_history_default PARTITION OF trip.trip_status_history DEFAULT;


CREATE OR REPLACE FUNCTION trip.log_trip_status_change() RETURNS TRIGGER AS $$
BEGIN
	IF NEW.status IS DISTINCT FROM OLD.status THEN
		INSERT INTO trip.trip_status_history (trip_id, from_status, to_status, actor_type, actor_id, reason, event_id, occurred_at)
		VALUES (
			NEW.id,
			OLD.status,
			NEW.status,
			COALESCE(NULLIF(current_setting('app.actor_type', true), '')::trip.actor_type, 'SYSTEM'),
			NULLIF(current_setting('app.actor_id', true), '')::UUID,
			NULLIF(current_setting('app.reason', true), ''),
			NULLIF(current_setting('app.event_id', true), '')::UUID,
			now()
		);
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ========================================================
--  V1
--  Module: dispatch
--  Goal: Manage the matching engine and driver assignment:
--      - Handle dispatch offers and assignment waves
--      - Track the status of ride requests sent to drivers
--      - Record distances and ETAs at the moment of the offer
-- ========================================================

CREATE SCHEMA IF NOT EXISTS dispatch;

CREATE TYPE dispatch.offer_status AS ENUM ('SENT','ACCEPTED','REJECTED','EXPIRED','CANCELLED');

CREATE TABLE dispatch.dispatch_offers (
	id UUID PRIMARY KEY,
	trip_id UUID NOT NULL,
	driver_id UUID NOT NULL,
	wave SMALLINT NOT NULL DEFAULT 1,
	rank SMALLINT NOT NULL,
	distance_m INT,
	eta_s INT,
	offered_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	expires_at TIMESTAMPTZ NOT NULL,
	status dispatch.offer_status NOT NULL DEFAULT 'SENT',
	responded_at TIMESTAMPTZ,

	UNIQUE (trip_id, driver_id, wave)
);

CREATE INDEX idx_offers_trip ON dispatch.dispatch_offers(trip_id, wave, rank);
CREATE INDEX idx_offers_driver ON dispatch.dispatch_offers(driver_id, offered_at DESC);

-- ========================================================
--  V1
--  Module: pricing
--  Goal: Provide fare estimation and record offline payments for the taxi fleet:
--      - Store active tariffs to allow the AI agent to quote estimated prices
--      - Keep an audit trail of quotes provided to customers to resolve disputes
--      - Record final trip receipts (cash/dataphone) for the fleet owner's statistics
-- ========================================================

CREATE SCHEMA IF NOT EXISTS pricing;

CREATE TYPE pricing.vehicle_type AS ENUM ('SEDAN','ESTATE','MINIVAN_6','VAN_9','LUXURY','EV','WHEELCHAIR_ACCESSIBLE');
CREATE TYPE pricing.payment_intent AS ENUM ('CASH', 'CARD_IN_CAR', 'COMPANY_ACCOUNT');

CREATE TABLE pricing.tariffs (
	id UUID PRIMARY KEY,
	name TEXT NOT NULL,
	zone_id UUID,
	vehicle_type pricing.vehicle_type,
	base_fare NUMERIC(10,2) NOT NULL,
	per_km NUMERIC(10,4) NOT NULL,
	per_min NUMERIC(10,4) NOT NULL,
	min_fare NUMERIC(10,2) NOT NULL,
	waiting_per_min NUMERIC(10,4) NOT NULL DEFAULT 0,
	night_surcharge_pct NUMERIC(5,2) NOT NULL DEFAULT 0,
	airport_surcharge NUMERIC(10,2) NOT NULL DEFAULT 0,
	valid_from DATE NOT NULL,
	valid_to DATE,
	active BOOLEAN NOT NULL DEFAULT true
);

CREATE INDEX idx_tariffs_lookup ON pricing.tariffs(zone_id, vehicle_type, valid_from) WHERE active;


CREATE TABLE pricing.fare_estimates (
    id uuid PRIMARY KEY,
    booking_id uuid NOT NULL,
    tariff_id uuid REFERENCES pricing.tariffs(id),
    estimated_min numeric(10,2) NOT NULL,
    estimated_max numeric(10,2) NOT NULL,
    currency char(3) NOT NULL DEFAULT 'EUR',
    calculated_at timestamptz NOT NULL DEFAULT now()
);


CREATE TABLE pricing.trip_receipts (
    id uuid PRIMARY KEY,
    trip_id uuid NOT NULL UNIQUE,
    payment_method pricing.payment_intent NOT NULL,
    total_amount numeric(10,2) NOT NULL,
    currency char(3) NOT NULL DEFAULT 'EUR',
    driver_notes text,
    recorded_at timestamptz NOT NULL DEFAULT now()
);

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

-- ========================================================
--  V1
--  Module: notification
--  Goal: Manage outbound communication configuration and user preferences:
--      - Centralize multi-language message templates for easy updates without code deployments
--      - Manage opt-in/opt-out rules per channel to ensure compliance (GDPR/spam)
--      - Handle "Do Not Disturb" quiet hours with accurate timezone awareness
-- ========================================================

CREATE SCHEMA IF NOT EXISTS notification;

CREATE TYPE notification.notification_channel AS ENUM ('SMS','PUSH','WHATSAPP','EMAIL','VOICE_CALL');
CREATE TYPE notification.actor_type AS ENUM ('PASSENGER', 'DRIVER', 'OPERATOR', 'SYSTEM');

CREATE TABLE notification.notification_templates (
	id UUID PRIMARY KEY,
	code TEXT NOT NULL,
	channel notification.notification_channel NOT NULL,
	language CHAR(2) NOT NULL DEFAULT 'es-ES',
	subject TEXT,
	body TEXT NOT NULL,
	version SMALLINT NOT NULL DEFAULT 1,
	active BOOLEAN NOT NULL DEFAULT true,

	UNIQUE (code, channel, language, version)
);


CREATE TABLE notification.notification_preferences (
	id UUID PRIMARY KEY,
	owner_type common.actor_type NOT NULL,
	owner_id UUID NOT NULL,
	channel notification.notification_channel NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT true,
	quiet_from TIME,
	quiet_to TIME,
    timezone   text NOT NULL DEFAULT 'Europe/Madrid',

	UNIQUE (owner_type, owner_id, channel)
);

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
