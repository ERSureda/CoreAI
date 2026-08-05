package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record EndVehicleAssignmentHttpRequest(
        @Schema(description = "Assignment identifier to end.", example = "e5f6a7b8-9c0d-1e2f-3a4b-5c6d7e8f9a0b")
        @NotNull(message = "ID_REQUIRED")
        UUID id
) {}
