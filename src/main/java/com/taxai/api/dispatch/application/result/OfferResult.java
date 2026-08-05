package com.taxai.api.dispatch.application.result;

import java.time.Instant;
import java.util.UUID;

public record OfferResult(
        UUID id,
        UUID tripId,
        UUID driverId,
        UUID vehicleId,
        Integer rank,
        Integer distanceM,
        Integer etaS,
        String status,
        Instant offeredAt,
        Instant expiresAt,
        Instant respondedAt
) {}
