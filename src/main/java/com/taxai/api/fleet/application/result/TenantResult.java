package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record TenantResult(
        UUID id,
        String name,
        String taxId,
        Boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {}
