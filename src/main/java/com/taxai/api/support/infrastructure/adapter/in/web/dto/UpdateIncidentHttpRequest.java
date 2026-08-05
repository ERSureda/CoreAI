package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateIncidentHttpRequest(
        @Schema(description = "Unique identifier of the incident.", example = "i1n2c3i4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated description.", example = "Additional details provided by passenger")
        String description,

        @Schema(description = "Updated priority level.", example = "URGENT")
        String priority
) {}
