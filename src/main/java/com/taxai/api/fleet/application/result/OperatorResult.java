package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record OperatorResult(
        UUID id,
        UUID tenantId,
        String fullName,
        String role,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {}
