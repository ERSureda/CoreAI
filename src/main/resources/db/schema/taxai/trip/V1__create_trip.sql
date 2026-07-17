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