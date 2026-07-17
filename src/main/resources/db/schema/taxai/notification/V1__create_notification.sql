-- ========================================================
--  V1
--  Module: notification
--  Goal: Manage outbound communication configuration and user preferences:
--      - Centralize multi-language message templates for easy updates without code deployments
--      - Manage opt-in/opt-out rules per channel to ensure compliance (GDPR/spam)
--      - Handle "Do Not Disturb" quiet hours with accurate timezone awareness
-- ========================================================

CREATE SCHEMA IF NOT EXISTS notification;

CREATE TYPE notification.notification_channel AS ENUM ('SMS','PUSH','WHATSAPP','EMAIL','VOICE_CALL');
CREATE TYPE notification.actor_type AS ENUM ('PASSENGER', 'DRIVER', 'OPERATOR', 'SYSTEM');

CREATE TABLE notification.notification_templates (
	id UUID PRIMARY KEY,
	code TEXT NOT NULL,
	channel notification.notification_channel NOT NULL,
	language CHAR(2) NOT NULL DEFAULT 'es-ES',
	subject TEXT,
	body TEXT NOT NULL,
	version SMALLINT NOT NULL DEFAULT 1,
	active BOOLEAN NOT NULL DEFAULT true,

	UNIQUE (code, channel, language, version)
);


CREATE TABLE notification.notification_preferences (
	id UUID PRIMARY KEY,
	owner_type common.actor_type NOT NULL,
	owner_id UUID NOT NULL,
	channel notification.notification_channel NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT true,
	quiet_from TIME,
	quiet_to TIME,
    timezone   text NOT NULL DEFAULT 'Europe/Madrid',

	UNIQUE (owner_type, owner_id, channel)
);
