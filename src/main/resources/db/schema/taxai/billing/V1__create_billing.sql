-- ========================================================
--  V1
--  Module: billing
-- ========================================================

CREATE SCHEMA IF NOT EXISTS billing;

CREATE TYPE billing.payment_method_type AS ENUM ('CASH','CARD','WALLET','INVOICE');
CREATE TYPE billing.payment_status AS ENUM ('PENDING','AUTHORIZED','CAPTURED','FAILED','REFUNDED','PARTIALLY_REFUNDED','CANCELLED');
CREATE TYPE billing.ledger_entry_type AS ENUM ('CREDIT','DEBIT');

CREATE TABLE billing.tariffs (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name TEXT NOT NULL,
	zone_id UUID REFERENCES registry.zones(id),
	vehicle_type registry.vehicle_type,
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

CREATE INDEX idx_tariffs_lookup ON billing.tariffs(zone_id, vehicle_type, valid_from) WHERE active;


CREATE TABLE billing.payment_methods (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	type billing.payment_method_type NOT NULL,
	provider TEXT,
	provider_token TEXT,
	card_last4 CHAR(4),
	card_brand TEXT,
	expires_month SMALLINT,
	expires_year SMALLINT,
	is_default BOOLEAN NOT NULL DEFAULT false,
	status TEXT NOT NULL DEFAULT 'ACTIVE',
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_payment_methods_pax ON billing.payment_methods(passenger_id) WHERE status = 'ACTIVE';


CREATE TABLE billing.payments (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	trip_id UUID NOT NULL REFERENCES booking.trips(id),
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	method_id UUID REFERENCES billing.payment_methods(id),
	type billing.payment_method_type NOT NULL,
	amount NUMERIC(10,2) NOT NULL CHECK (amount >= 0),
	currency CHAR(3) NOT NULL DEFAULT 'EUR',
	status billing.payment_status NOT NULL DEFAULT 'PENDING',
	provider_ref TEXT,
	idempotency_key TEXT UNIQUE,
	failure_reason TEXT,
	authorized_at TIMESTAMPTZ,
	captured_at TIMESTAMPTZ,
	refunded_at TIMESTAMPTZ,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_payments_trip ON billing.payments(trip_id);
CREATE INDEX idx_payments_status ON billing.payments(status, created_at) WHERE status IN ('PENDING','AUTHORIZED');

CREATE TRIGGER trg_payments_updated BEFORE UPDATE ON billing.payments
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE billing.wallets (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	passenger_id UUID NOT NULL UNIQUE REFERENCES registry.passengers(id),
	currency CHAR(3) NOT NULL DEFAULT 'EUR',
	balance NUMERIC(12,2) NOT NULL DEFAULT 0,
	updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TRIGGER trg_wallets_updated BEFORE UPDATE ON billing.wallets
	FOR EACH ROW EXECUTE FUNCTION common.set_updated_at();


CREATE TABLE billing.wallet_ledger (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	wallet_id UUID NOT NULL REFERENCES billing.wallets(id),
	entry_type billing.ledger_entry_type NOT NULL,
	amount NUMERIC(12,2) NOT NULL CHECK (amount > 0),
	balance_after NUMERIC(12,2) NOT NULL,
	trip_id UUID REFERENCES booking.trips(id),
	payment_id UUID REFERENCES billing.payments(id),
	description TEXT NOT NULL,
	idempotency_key TEXT UNIQUE,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX idx_wallet_ledger ON billing.wallet_ledger(wallet_id, id DESC);


CREATE OR REPLACE FUNCTION billing.sync_wallet_balance() RETURNS trigger AS $$
BEGIN
	UPDATE billing.wallets
		 SET balance = NEW.balance_after, updated_at = now()
	 WHERE id = NEW.wallet_id;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_wallet_ledger_balance AFTER INSERT ON billing.wallet_ledger
	FOR EACH ROW EXECUTE FUNCTION billing.sync_wallet_balance();


CREATE TABLE billing.billing_accounts (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	company_name TEXT NOT NULL,
	tax_id TEXT NOT NULL UNIQUE,
	billing_address TEXT NOT NULL,
	billing_email TEXT NOT NULL,
	payment_terms_days SMALLINT NOT NULL DEFAULT 30,
	active BOOLEAN NOT NULL DEFAULT true,
	created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);


CREATE TABLE billing.passenger_billing_accounts (
	passenger_id UUID NOT NULL REFERENCES registry.passengers(id),
	billing_account_id UUID NOT NULL REFERENCES billing.billing_accounts(id),
	is_default BOOLEAN NOT NULL DEFAULT false,
	PRIMARY KEY (passenger_id, billing_account_id)
);


CREATE TABLE billing.invoices (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	series TEXT NOT NULL,
	number BIGINT NOT NULL,
	billing_account_id UUID REFERENCES billing.billing_accounts(id),
	passenger_id UUID REFERENCES registry.passengers(id),
	period_start DATE,
	period_end DATE,
	subtotal NUMERIC(12,2) NOT NULL,
	tax_amount NUMERIC(12,2) NOT NULL,
	total NUMERIC(12,2) NOT NULL,
	status TEXT NOT NULL DEFAULT 'ISSUED',
	pdf_url TEXT,
	issued_at TIMESTAMPTZ NOT NULL DEFAULT now(),
	UNIQUE (series, number),
	CHECK (billing_account_id IS NOT NULL OR passenger_id IS NOT NULL)
);


CREATE TABLE billing.invoice_lines (
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	invoice_id UUID NOT NULL REFERENCES billing.invoices(id) ON DELETE CASCADE,
	trip_id UUID REFERENCES booking.trips(id),
	description TEXT NOT NULL,
	amount NUMERIC(10,2) NOT NULL,
	tax_rate NUMERIC(5,2) NOT NULL DEFAULT 10.00
);

CREATE INDEX idx_invoice_lines ON billing.invoice_lines(invoice_id);
