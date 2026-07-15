-- ========================================================
--  V1
--  Module: booking
-- ========================================================

CREATE SCHEMA IF NOT EXISTS booking;

CREATE TYPE booking.booking_channel AS ENUM ('PHONE_AI','PHONE_HUMAN','APP','WEB','BACKOFFICE','PARTNER');
CREATE TYPE booking.booking_type AS ENUM ('IMMEDIATE','SCHEDULED','RECURRING');
CREATE TYPE booking.booking_status AS ENUM ('PENDING_CONFIRMATION','CONFIRMED','NEEDS_REVIEW','CANCELLED','EXPIRED','FULFILLED');
CREATE TYPE booking.trip_status AS ENUM ('REQUESTED','SCHEDULED','SEARCHING','OFFERED','ASSIGNED','ACCEPTED','ARRIVING','WAITING','ON_BOARD','COMPLETED','CANCELLED','NO_SHOW','EXPIRED','FAILED');
CREATE TYPE booking.stop_type AS ENUM ('PICKUP','INTERMEDIATE','DROPOFF');
CREATE TYPE booking.stop_status AS ENUM ('PENDING','ARRIVED','DEPARTED','SKIPPED');
CREATE TYPE booking.cancel_reason AS ENUM ('PASSENGER_REQUEST','DRIVER_REQUEST','NO_SHOW','NO_DRIVERS_AVAILABLE','OPERATIONAL','DUPLICATE','PRICE_DISAGREEMENT','WEATHER','FRAUD','OTHER');
CREATE TYPE booking.offer_status AS ENUM ('SENT','ACCEPTED','REJECTED','EXPIRED','CANCELLED');
CREATE TYPE booking.luggage_type AS ENUM ('SMALL','MEDIUM','LARGE','SPECIAL','BICYCLE','WHEELCHAIR','STROLLER','SKI','OTHER');
CREATE TYPE booking.route_reason AS ENUM ('INITIAL','TRAFFIC_UPDATE','DESTINATION_CHANGE','STOP_ADDED','DEVIATION_DETECTED','MANUAL');

CREATE TABLE booking.bookings (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	locator CHAR(8) NOT NULL UNIQUE,
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	channel booking.booking_channel NOT NULL,
	type booking.booking_type NOT NULL,
	status booking.booking_status NOT NULL DEFAULT 'CONFIRMED',
	scheduled_pickup_at TIMESTAMPTZ,
	pickup_timezone TEXT NOT NULL DEFAULT 'Europe/Madrid',
	recurrence_rule TEXT,
	recurrence_until DATE,
	passenger_count SMALLINT NOT NULL DEFAULT 1 CHECK (passenger_count BETWEEN 1 AND 16),
	vehicle_type_required registry.vehicle_type,
	needs_child_seat BOOLEAN NOT NULL DEFAULT false,
	wheelchair_required BOOLEAN NOT NULL DEFAULT false,
	notes TEXT,
	source_conversation_id TEXT,
	created_by_actor common.actor_type NOT NULL,
	created_by_id UUID,
	version INT NOT NULL DEFAULT 0,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CHECK (type <> 'SCHEDULED' OR scheduled_pickup_at IS NOT NULL),
	CHECK (type <> 'RECURRING' OR recurrence_rule IS NOT NULL)
);

CREATE INDEX idx_bookings_passenger ON booking.bookings(passenger_id, created_at DESC);
CREATE INDEX idx_bookings_recurring ON booking.bookings(recurrence_until) WHERE type = 'RECURRING' AND status = 'CONFIRMED';
CREATE INDEX idx_bookings_conversation ON booking.bookings(source_conversation_id) WHERE source_conversation_id IS NOT NULL;

CREATE TRIGGER trg_bookings_updated BEFORE UPDATE ON booking.bookings
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE booking.booking_luggage (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	booking_id UUID NOT NULL REFERENCES booking.bookings(id) ON DELETE CASCADE,
	type booking.luggage_type NOT NULL,
	quantity SMALLINT NOT NULL DEFAULT 1 CHECK (quantity > 0),
	notes TEXT
);

CREATE INDEX idx_booking_luggage ON booking.booking_luggage(booking_id);


CREATE TABLE booking.booking_pets (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	booking_id UUID NOT NULL REFERENCES booking.bookings(id) ON DELETE CASCADE,
	type registry.pet_type NOT NULL,
	quantity SMALLINT NOT NULL DEFAULT 1,
	in_carrier BOOLEAN NOT NULL DEFAULT false,
	notes TEXT
);

CREATE INDEX idx_booking_pets ON booking.booking_pets(booking_id);


CREATE TABLE booking.trips (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	booking_id UUID NOT NULL REFERENCES booking.bookings(id),
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	status booking.trip_status NOT NULL DEFAULT 'REQUESTED',
	driver_id UUID REFERENCES registry.drivers(id),
	vehicle_id UUID REFERENCES registry.vehicles(id),
	replaces_trip_id UUID REFERENCES booking.trips(id),
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
	cancelled_by common.actor_type,
	cancel_reason booking.cancel_reason,
	cancel_note TEXT,
	estimated_distance_m INT,
	estimated_duration_s INT,
	actual_distance_m INT,
	actual_duration_s INT,
	fare_quoted NUMERIC(10,2),
	fare_final NUMERIC(10,2),
	currency CHAR(3) NOT NULL DEFAULT 'EUR',
	active_route_version SMALLINT NOT NULL DEFAULT 0,
	version INT NOT NULL DEFAULT 0,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	CHECK (status <> 'CANCELLED' OR cancelled_by IS NOT NULL)
);

CREATE INDEX idx_trips_booking ON booking.trips(booking_id);
CREATE INDEX idx_trips_passenger ON booking.trips(passenger_id, created_at DESC);
CREATE INDEX idx_trips_driver ON booking.trips(driver_id, created_at DESC) WHERE driver_id IS NOT NULL;
CREATE UNIQUE INDEX uq_driver_active_trip ON booking.trips(driver_id) WHERE status IN ('ASSIGNED','ACCEPTED','ARRIVING','WAITING','ON_BOARD');
CREATE INDEX idx_trips_scheduled ON booking.trips(scheduled_pickup_at) WHERE status = 'SCHEDULED';
CREATE INDEX idx_trips_searching ON booking.trips(search_expires_at) WHERE status = 'SEARCHING';
CREATE INDEX idx_trips_active_ops ON booking.trips(status, updated_at DESC) WHERE status IN ('SEARCHING','OFFERED','ASSIGNED','ACCEPTED','ARRIVING','WAITING','ON_BOARD');

CREATE TRIGGER trg_trips_updated BEFORE UPDATE ON booking.trips
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE booking.trip_stops (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	trip_id UUID NOT NULL REFERENCES booking.trips(id) ON DELETE CASCADE,
	seq SMALLINT NOT NULL,
	type booking.stop_type NOT NULL,
	status booking.stop_status NOT NULL DEFAULT 'PENDING',
	location_id UUID REFERENCES registry.locations(id),
	address_snapshot JSONB NOT NULL,
	contact_name TEXT,
	contact_phone TEXT,
	eta_at TIMESTAMPTZ,
	arrived_at TIMESTAMPTZ,
	departed_at TIMESTAMPTZ,
	notes TEXT,
	UNIQUE (trip_id, seq)
);

CREATE INDEX idx_trip_stops_trip ON booking.trip_stops(trip_id, seq);


CREATE TABLE booking.trip_status_history (
	id BIGINT GENERATED ALWAYS AS IDENTITY,
	trip_id UUID NOT NULL,
	from_status booking.trip_status,
	to_status booking.trip_status NOT NULL,
	actor_type common.actor_type NOT NULL,
	actor_id UUID,
	reason TEXT,
	event_id UUID,
	metadata JSONB NOT NULL DEFAULT '{}'::jsonb,
	occurred_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	PRIMARY KEY (id, occurred_at)
) PARTITION BY RANGE (occurred_at);

CREATE INDEX idx_tsh_trip ON booking.trip_status_history(trip_id, occurred_at);


CREATE TABLE booking.trip_status_history_default PARTITION OF booking.trip_status_history DEFAULT;


CREATE TABLE booking.dispatch_offers (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	trip_id UUID NOT NULL REFERENCES booking.trips(id),
	driver_id UUID NOT NULL REFERENCES registry.drivers(id),
	wave SMALLINT NOT NULL DEFAULT 1,
	rank SMALLINT NOT NULL,
	distance_m INT,
	eta_s INT,
	offered_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	expires_at TIMESTAMPTZ NOT NULL,
	status booking.offer_status NOT NULL DEFAULT 'SENT',
	responded_at TIMESTAMPTZ,
	UNIQUE (trip_id, driver_id, wave)
);

CREATE INDEX idx_offers_trip ON booking.dispatch_offers(trip_id, wave, rank);
CREATE INDEX idx_offers_driver ON booking.dispatch_offers(driver_id, offered_at DESC);


CREATE TABLE booking.trip_routes (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	trip_id UUID NOT NULL REFERENCES booking.trips(id) ON DELETE CASCADE,
	route_version SMALLINT NOT NULL,
	reason booking.route_reason NOT NULL,
	provider TEXT NOT NULL DEFAULT 'GOOGLE_ROUTES',
	encoded_polyline TEXT NOT NULL,
	distance_m INT NOT NULL,
	duration_s INT NOT NULL,
	duration_traffic_s INT,
	toll_info JSONB,
	waypoints JSONB,
	computed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (trip_id, route_version)
);


CREATE OR REPLACE FUNCTION booking.log_trip_status_change() RETURNS trigger AS $$
BEGIN
	IF NEW.status IS DISTINCT FROM OLD.status THEN
		INSERT INTO booking.trip_status_history
			(trip_id, from_status, to_status, actor_type, actor_id, reason, event_id, occurred_at)
		VALUES
			(NEW.id,
			 OLD.status,
			 NEW.status,
			 COALESCE(NULLIF(current_setting('app.actor_type', true), '')::common.actor_type, 'SYSTEM'),
			 NULLIF(current_setting('app.actor_id', true), '')::uuid,
			 NULLIF(current_setting('app.reason', true), ''),
			 NULLIF(current_setting('app.event_id', true), '')::uuid,
			 now());
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_trip_status_history AFTER UPDATE OF status ON booking.trips
	FOR EACH ROW EXECUTE FUNCTION booking.log_trip_status_change();
