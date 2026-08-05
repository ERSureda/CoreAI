package com.taxai.api.support.application.result;

import java.time.Instant;
import java.util.UUID;

public record LostItemResult(
        UUID id,
        UUID tripId,
        UUID incidentId,
        String description,
        String status,
        String storageLocation,
        String contactPhone,
        Instant foundAt,
        Instant returnedAt,
        Instant createdAt
) {}
