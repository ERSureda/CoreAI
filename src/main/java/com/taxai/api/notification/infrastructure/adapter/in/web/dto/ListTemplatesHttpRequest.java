package com.taxai.api.notification.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record ListTemplatesHttpRequest(
        @Schema(description = "Optional template code filter.", example = "TRIP_ASSIGNED")
        String code,

        @Schema(description = "Optional channel filter.", example = "SMS")
        String channel,

        @Schema(description = "Optional ISO 2-letter language filter.", example = "ES")
        @Size(min = 2, max = 2, message = "LANGUAGE_INVALID")
        String language
) {}
