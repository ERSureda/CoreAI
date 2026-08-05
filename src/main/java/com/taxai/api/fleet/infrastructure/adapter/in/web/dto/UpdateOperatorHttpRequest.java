package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateOperatorHttpRequest(
        @Schema(description = "Unique identifier of the operator.", example = "b2c3d4e5-f6a7-8b9c-0d1e-2f3a4b5c6d7e")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated operator role.", example = "FLEET_MANAGER")
        String role,

        @Schema(description = "Active status flag.", example = "true")
        Boolean isActive
) {}
