-- ========================================================
--  V1
--  Module: registry
-- ========================================================

CREATE SCHEMA IF NOT EXISTS registry;

CREATE TYPE registry.zone_kind AS ENUM ('SERVICE_AREA','TARIFF_ZONE','AIRPORT','STATION','RESTRICTED');
CREATE TYPE registry.vehicle_type AS ENUM ('SEDAN','ESTATE','MINIVAN_6','VAN_9','LUXURY','EV','WHEELCHAIR_ACCESSIBLE');
CREATE TYPE registry.vehicle_status AS ENUM ('ACTIVE','MAINTENANCE','INACTIVE','RETIRED');
CREATE TYPE registry.driver_admin_status AS ENUM ('ONBOARDING','ACTIVE','SUSPENDED','INACTIVE');
CREATE TYPE registry.document_type AS ENUM ('DRIVER_LICENSE','TAXI_LICENSE','MUNICIPAL_PERMIT','ID_CARD','INSURANCE','ITV','CRIMINAL_RECORD_CERT','VEHICLE_REGISTRATION');
CREATE TYPE registry.document_status AS ENUM ('PENDING_REVIEW','VALID','REJECTED','EXPIRED');
CREATE TYPE registry.pet_type AS ENUM ('SMALL_IN_CARRIER','DOG_MEDIUM','DOG_LARGE','GUIDE_DOG','OTHER');

CREATE TABLE registry.operators (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	full_name TEXT NOT NULL,
	email TEXT NOT NULL UNIQUE,
	role TEXT NOT NULL DEFAULT 'AGENT',
	is_active BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);


CREATE TABLE registry.locations (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
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

CREATE INDEX idx_locations_geohash ON registry.locations(geohash7);


CREATE TABLE registry.zones (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name TEXT NOT NULL,
	kind registry.zone_kind NOT NULL,
	geom GEOGRAPHY(MultiPolygon, 4326) NOT NULL,
	active BOOLEAN NOT NULL DEFAULT true,
	extra JSONB NOT NULL DEFAULT '{}'::jsonb
);

CREATE INDEX idx_zones_geom ON registry.zones USING gist(geom);


CREATE TABLE registry.vehicles (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	plate TEXT NOT NULL UNIQUE,
	make TEXT NOT NULL,
	model TEXT NOT NULL,
	year SMALLINT,
	color TEXT,
	type registry.vehicle_type NOT NULL,
	status registry.vehicle_status NOT NULL DEFAULT 'ACTIVE',
	passenger_seats SMALLINT NOT NULL CHECK (passenger_seats BETWEEN 1 AND 16),
	max_suitcases SMALLINT NOT NULL DEFAULT 2,
	allows_pets BOOLEAN NOT NULL DEFAULT false,
	has_child_seat BOOLEAN NOT NULL DEFAULT false,
	child_seat_count SMALLINT NOT NULL DEFAULT 0,
	wheelchair_accessible BOOLEAN NOT NULL DEFAULT false,
	has_bike_rack BOOLEAN NOT NULL DEFAULT false,
	extra_features JSONB NOT NULL DEFAULT '{}'::jsonb,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_vehicles_matching ON registry.vehicles(type, status) WHERE status = 'ACTIVE';

CREATE TRIGGER trg_vehicles_updated BEFORE UPDATE ON registry.vehicles
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE registry.drivers (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	employee_code TEXT NOT NULL UNIQUE,
	full_name TEXT NOT NULL,
	phone TEXT NOT NULL UNIQUE CHECK (phone ~ '^\+[1-9][0-9]{6,14}$'),
	email TEXT UNIQUE,
	taxi_license_number TEXT UNIQUE,
	admin_status registry.driver_admin_status NOT NULL DEFAULT 'ONBOARDING',
	default_vehicle_id UUID REFERENCES registry.vehicles(id),
	rating_avg NUMERIC(3,2),
	rating_count INT NOT NULL DEFAULT 0,
	last_seen_at TIMESTAMPTZ,
	hired_at DATE,
	deleted_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER trg_drivers_updated BEFORE UPDATE ON registry.drivers
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE registry.driver_documents (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	driver_id UUID NOT NULL REFERENCES registry.drivers(id),
	type registry.document_type NOT NULL,
	number TEXT,
	issued_at DATE,
	expires_at DATE,
	status registry.document_status NOT NULL DEFAULT 'PENDING_REVIEW',
	file_url TEXT,
	reviewed_by UUID REFERENCES registry.operators(id),
	reviewed_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_driver_documents_driver ON registry.driver_documents(driver_id, type);
CREATE INDEX idx_driver_documents_expiry ON registry.driver_documents(expires_at) WHERE status = 'VALID';

CREATE TRIGGER trg_driver_documents_updated BEFORE UPDATE ON registry.driver_documents
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE registry.vehicle_documents (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	vehicle_id UUID NOT NULL REFERENCES registry.vehicles(id),
	type registry.document_type NOT NULL,
	number TEXT,
	issued_at DATE,
	expires_at DATE,
	status registry.document_status NOT NULL DEFAULT 'PENDING_REVIEW',
	file_url TEXT,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_vehicle_documents_vehicle ON registry.vehicle_documents(vehicle_id, type);


CREATE TABLE registry.driver_vehicle_assignments (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	driver_id UUID NOT NULL REFERENCES registry.drivers(id),
	vehicle_id UUID NOT NULL REFERENCES registry.vehicles(id),
	valid_from TIMESTAMPTZ NOT NULL DEFAULT now(),
	valid_to TIMESTAMPTZ,
	created_by UUID REFERENCES registry.operators(id),
	EXCLUDE USING gist (vehicle_id WITH =, tstzrange(valid_from, valid_to) WITH &&)
);

CREATE INDEX idx_dva_driver ON registry.driver_vehicle_assignments(driver_id, valid_from DESC);
CREATE UNIQUE INDEX uq_dva_driver_current ON registry.driver_vehicle_assignments(driver_id) WHERE valid_to IS NULL;


CREATE TABLE registry.passengers (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	full_name TEXT NOT NULL,
	primary_phone TEXT NOT NULL UNIQUE CHECK (primary_phone ~ '^\+[1-9][0-9]{6,14}$'),
	email TEXT UNIQUE,
	language CHAR(2) NOT NULL DEFAULT 'es',
	marketing_consent BOOLEAN NOT NULL DEFAULT false,
	is_vip BOOLEAN NOT NULL DEFAULT false,
	notes TEXT,
	gdpr_erased_at TIMESTAMPTZ,
	deleted_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER trg_passengers_updated BEFORE UPDATE ON registry.passengers
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE registry.passenger_phones (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	phone TEXT NOT NULL UNIQUE CHECK (phone ~ '^\+[1-9][0-9]{6,14}$'),
	label TEXT,
	verified_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_passenger_phones_passenger ON registry.passenger_phones(passenger_id);


CREATE TABLE registry.passenger_addresses (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	alias TEXT,
	location_id UUID NOT NULL REFERENCES registry.locations(id),
	entrance_note TEXT,
	is_favorite BOOLEAN NOT NULL DEFAULT false,
	usage_count INT NOT NULL DEFAULT 0,
	last_used_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (passenger_id, alias)
);

CREATE INDEX idx_passenger_addresses_pax ON registry.passenger_addresses(passenger_id, usage_count DESC);


CREATE TABLE registry.passenger_preferences (
	passenger_id UUID PRIMARY KEY REFERENCES registry.passengers(id),
	default_vehicle_type registry.vehicle_type,
	needs_child_seat BOOLEAN NOT NULL DEFAULT false,
	travels_with_pet registry.pet_type,
	wheelchair_user BOOLEAN NOT NULL DEFAULT false,
	preferred_driver_id UUID REFERENCES registry.drivers(id),
	blocked_driver_ids UUID[] NOT NULL DEFAULT '{}',
	extra JSONB NOT NULL DEFAULT '{}'::jsonb,
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER trg_passenger_preferences_updated BEFORE UPDATE ON registry.passenger_preferences
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();
