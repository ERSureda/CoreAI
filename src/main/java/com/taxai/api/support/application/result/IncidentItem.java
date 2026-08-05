package com.taxai.api.support.application.result;

import java.time.Instant;
import java.util.UUID;

public record IncidentItem(
        UUID id,
        String type,
        String status,
        String priority,
        Instant createdAt
) {}
