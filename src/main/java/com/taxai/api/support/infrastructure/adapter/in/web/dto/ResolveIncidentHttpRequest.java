package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ResolveIncidentHttpRequest(
        @Schema(description = "Unique identifier of the incident.", example = "i1n2c3i4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Resolution summary description.", example = "Refund processed to passenger card")
        @NotBlank(message = "RESOLUTION_REQUIRED")
        String resolution
) {}
