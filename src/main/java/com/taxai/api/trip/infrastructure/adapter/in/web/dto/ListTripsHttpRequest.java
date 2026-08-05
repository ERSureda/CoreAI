package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ListTripsHttpRequest(
        @Schema(description = "Tenant identifier to filter trips.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Optional trip status filter.", example = "IN_PROGRESS")
        String status,

        @Schema(description = "Optional assigned driver identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        UUID driverId,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
