package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ListBookingsHttpRequest(
        @Schema(description = "Tenant identifier to filter bookings.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Optional booking status filter.", example = "CONFIRMED")
        String status,

        @Schema(description = "Optional passenger identifier filter.", example = "p1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        UUID passengerId,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
