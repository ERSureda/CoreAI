package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateOperatorHttpRequest(
        @Schema(description = "Tenant identifier to which this operator belongs.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Associated user profile identifier.", example = "f47ac10b-58cc-4372-a567-0e02b2c3d4e5")
        @NotNull(message = "USER-ID_REQUIRED")
        UUID userId,

        @Schema(description = "Full name of the fleet operator.", example = "Maria Garcia")
        @NotBlank(message = "FULL-NAME_REQUIRED")
        String fullName,

        @Schema(description = "Role assigned to the operator.", example = "DISPATCHER")
        @NotBlank(message = "ROLE_REQUIRED")
        String role
) {}
