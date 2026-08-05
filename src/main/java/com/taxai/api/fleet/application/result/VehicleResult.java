package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record VehicleResult(
        UUID id,
        UUID tenantId,
        String plate,
        String make,
        String model,
        String type,
        String status,
        Integer passengerSeats,
        Boolean wheelchairAccessible,
        Boolean allowsPets,
        Instant createdAt,
        Instant updatedAt
) {}
