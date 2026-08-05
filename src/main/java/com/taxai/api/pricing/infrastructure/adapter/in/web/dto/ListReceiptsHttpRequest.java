package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

public record ListReceiptsHttpRequest(
        @Schema(description = "Tenant identifier.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Start timestamp for receipt range in UTC.", example = "2026-08-01T00:00:00Z")
        @NotNull(message = "FROM_REQUIRED")
        Instant from,

        @Schema(description = "End timestamp for receipt range in UTC.", example = "2026-08-31T23:59:59Z")
        @NotNull(message = "TO_REQUIRED")
        Instant to,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
