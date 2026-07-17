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