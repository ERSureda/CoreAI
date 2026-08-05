package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateFareEstimateHttpRequest(
        @Schema(description = "Tenant identifier.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Optional booking identifier.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        UUID bookingId,

        @Schema(description = "Origin address or location.", example = "Calle Alcala 100, Madrid")
        @NotBlank(message = "ORIGIN_REQUIRED")
        String origin,

        @Schema(description = "Destination address or location.", example = "Airport T4, Madrid")
        @NotBlank(message = "DESTINATION_REQUIRED")
        String destination,

        @Schema(description = "Vehicle category type.", example = "SEDAN")
        @NotBlank(message = "VEHICLE-TYPE_REQUIRED")
        String vehicleType
) {}
