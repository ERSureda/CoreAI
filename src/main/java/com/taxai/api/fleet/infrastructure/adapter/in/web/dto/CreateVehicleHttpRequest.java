package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateVehicleHttpRequest(
        @Schema(description = "Tenant identifier owning the vehicle.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Vehicle license plate number.", example = "1234-BBB")
        @NotBlank(message = "PLATE_REQUIRED")
        String plate,

        @Schema(description = "Vehicle manufacturer.", example = "Toyota")
        @NotBlank(message = "MAKE_REQUIRED")
        String make,

        @Schema(description = "Vehicle model name.", example = "Prius")
        @NotBlank(message = "MODEL_REQUIRED")
        String model,

        @Schema(description = "Vehicle category type.", example = "HYBRID")
        @NotBlank(message = "TYPE_REQUIRED")
        String type,

        @Schema(description = "Number of passenger seats available.", example = "4")
        @NotNull(message = "PASSENGER-SEATS_REQUIRED")
        @Min(value = 1, message = "PASSENGER-SEATS_INVALID")
        Integer passengerSeats,

        @Schema(description = "Flag indicating wheelchair accessibility.", example = "true")
        @NotNull(message = "WHEELCHAIR-ACCESSIBLE_REQUIRED")
        Boolean wheelchairAccessible,

        @Schema(description = "Flag indicating pet permission.", example = "true")
        @NotNull(message = "ALLOWS-PETS_REQUIRED")
        Boolean allowsPets
) {}
