package com.taxai.api.audit.application.result;

import java.time.Instant;
import java.util.UUID;

public record AuditLogResult(
        UUID id,
        Instant occurredAt,
        String aggregateType,
        UUID aggregateId,
        String action,
        String actorType,
        UUID actorId,
        String source,
        UUID eventId
) {}
