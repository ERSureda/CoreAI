package com.taxai.api.support.application.result;

import java.time.Instant;
import java.util.UUID;

public record IncidentResult(
        UUID id,
        UUID tripId,
        String type,
        String status,
        String priority,
        String reportedBy,
        UUID reporterId,
        UUID assigneeId,
        String title,
        String description,
        String resolution,
        Instant createdAt,
        Instant resolvedAt,
        Instant updatedAt
) {}
