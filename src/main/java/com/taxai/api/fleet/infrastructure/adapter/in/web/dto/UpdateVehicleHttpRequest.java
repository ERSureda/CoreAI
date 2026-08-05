package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateVehicleHttpRequest(
        @Schema(description = "Unique identifier of the vehicle.", example = "c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated manufacturer.", example = "Toyota")
        String make,

        @Schema(description = "Updated model.", example = "Camry")
        String model,

        @Schema(description = "Updated passenger seats count.", example = "4")
        Integer passengerSeats,

        @Schema(description = "Updated wheelchair accessibility flag.", example = "true")
        Boolean wheelchairAccessible,

        @Schema(description = "Updated pets permission flag.", example = "false")
        Boolean allowsPets
) {}
