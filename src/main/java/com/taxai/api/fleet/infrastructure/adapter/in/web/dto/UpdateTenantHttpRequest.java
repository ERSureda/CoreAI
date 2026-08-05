package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateTenantHttpRequest(
        @Schema(description = "Unique identifier of the tenant.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated legal name of the tenant enterprise.", example = "Taxi Central Global")
        String name,

        @Schema(description = "Operational status flag for the tenant.", example = "true")
        Boolean isActive
) {}
