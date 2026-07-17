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