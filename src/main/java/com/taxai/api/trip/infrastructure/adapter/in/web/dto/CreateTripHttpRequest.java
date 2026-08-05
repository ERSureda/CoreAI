package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateTripHttpRequest(
        @Schema(description = "Tenant identifier.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Source booking identifier.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "BOOKING-ID_REQUIRED")
        UUID bookingId,

        @Schema(description = "Passenger identifier.", example = "p1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        @NotNull(message = "PASSENGER-ID_REQUIRED")
        UUID passengerId
) {}
