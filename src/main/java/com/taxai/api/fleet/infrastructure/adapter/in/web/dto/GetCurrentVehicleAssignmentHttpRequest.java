package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetCurrentVehicleAssignmentHttpRequest(
        @Schema(description = "Driver identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "DRIVER-ID_REQUIRED")
        UUID driverId
) {}
