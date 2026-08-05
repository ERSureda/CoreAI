package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateVehicleStatusHttpRequest(
        @Schema(description = "Unique identifier of the vehicle.", example = "c3d4e5f6-a7b8-9c0d-1e2f-3a4b5c6d7e8f")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "New operational status.", example = "MAINTENANCE")
        @NotBlank(message = "STATUS_REQUIRED")
        String status
) {}
