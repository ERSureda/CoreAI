package com.taxai.api.trip.application.result;

import java.time.Instant;
import java.util.UUID;

public record TenantTripsLiveEventResult(
        UUID tripId,
        String status,
        UUID driverId,
        Instant updatedAt
) {}
