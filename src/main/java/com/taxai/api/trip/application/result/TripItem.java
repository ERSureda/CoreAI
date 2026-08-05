package com.taxai.api.trip.application.result;

import java.time.Instant;
import java.util.UUID;

public record TripItem(
        UUID id,
        String status,
        UUID driverId,
        Instant createdAt
) {}
