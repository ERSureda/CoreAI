package com.taxai.api.notification.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateTemplateHttpRequest(
        @Schema(description = "Template identifier.", example = "t1e2m3p4-l5a6-7t8e-9i0d-1e2f3a4b5c6d")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated template message body.", example = "Updated message body for {driverName}")
        String body,

        @Schema(description = "Active status flag.", example = "true")
        Boolean active
) {}
