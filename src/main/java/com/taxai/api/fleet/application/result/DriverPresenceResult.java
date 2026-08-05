package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record DriverPresenceResult(
        UUID driverId,
        String status,
        UUID zoneId,
        Instant updatedAt
) {}
