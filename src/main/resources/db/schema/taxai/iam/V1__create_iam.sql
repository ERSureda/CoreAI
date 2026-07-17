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