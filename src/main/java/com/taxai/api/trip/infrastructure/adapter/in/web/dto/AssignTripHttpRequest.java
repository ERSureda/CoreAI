package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AssignTripHttpRequest(
        @Schema(description = "Unique identifier of the trip.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Driver identifier to assign.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "DRIVER-ID_REQUIRED")
        UUID driverId,

        @Schema(description = "Vehicle identifier to assign.", example = "c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
        @NotNull(message = "VEHICLE-ID_REQUIRED")
        UUID vehicleId
) {}
