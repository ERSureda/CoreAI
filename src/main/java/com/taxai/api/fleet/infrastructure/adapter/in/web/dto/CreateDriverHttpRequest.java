package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateDriverHttpRequest(
        @Schema(description = "Tenant identifier owning the driver.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Optional user account ID.", example = "f47ac10b-58cc-4372-a567-0e02b2c3d4e5")
        UUID userId,

        @Schema(description = "Internal employee code.", example = "DRV-1024")
        @NotBlank(message = "EMPLOYEE-CODE_REQUIRED")
        String employeeCode,

        @Schema(description = "Full name of the driver.", example = "Carlos Lopez")
        @NotBlank(message = "FULL-NAME_REQUIRED")
        String fullName,

        @Schema(description = "Contact phone number.", example = "+34612345678")
        @NotBlank(message = "PHONE_REQUIRED")
        String phone
) {}
