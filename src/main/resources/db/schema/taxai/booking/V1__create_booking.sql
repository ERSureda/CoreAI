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