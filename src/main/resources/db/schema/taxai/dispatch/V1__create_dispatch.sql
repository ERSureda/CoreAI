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
