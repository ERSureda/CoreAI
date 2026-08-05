package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record DriverResult(
        UUID id,
        UUID tenantId,
        String employeeCode,
        String fullName,
        String phone,
        String adminStatus,
        UUID defaultVehicleId,
        Instant createdAt,
        Instant updatedAt
) {}
