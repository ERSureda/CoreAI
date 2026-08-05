package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateVehicleAssignmentHttpRequest(
        @Schema(description = "Driver identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "DRIVER-ID_REQUIRED")
        UUID driverId,

        @Schema(description = "Vehicle identifier.", example = "c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
        @NotNull(message = "VEHICLE-ID_REQUIRED")
        UUID vehicleId,

        @Schema(description = "Operator or user who created the assignment.", example = "f47ac10b-58cc-4372-a567-0e02b2c3d4e5")
        @NotNull(message = "CREATED-BY_REQUIRED")
        UUID createdBy
) {}
