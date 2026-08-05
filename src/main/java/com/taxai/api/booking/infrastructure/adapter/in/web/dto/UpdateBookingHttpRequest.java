package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record UpdateBookingHttpRequest(
        @Schema(description = "Unique identifier of the booking.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated scheduled pickup time in UTC.", example = "2026-08-06T10:30:00Z")
        Instant scheduledPickupAt,

        @Schema(description = "Updated required vehicle type.", example = "VAN")
        String vehicleTypeRequired,

        @Schema(description = "Updated additional notes.", example = "Wait at gate 3")
        String notes
) {}
