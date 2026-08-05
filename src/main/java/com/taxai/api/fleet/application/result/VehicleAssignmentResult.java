package com.taxai.api.fleet.application.result;

import java.time.Instant;
import java.util.UUID;

public record VehicleAssignmentResult(
        UUID id,
        UUID driverId,
        UUID vehicleId,
        Instant validFrom,
        Instant validTo
) {}
