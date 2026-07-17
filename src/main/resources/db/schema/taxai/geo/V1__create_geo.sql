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
