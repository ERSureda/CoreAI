package com.taxai.api.notification.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTemplateHttpRequest(
        @Schema(description = "Template unique code key.", example = "TRIP_ASSIGNED")
        @NotBlank(message = "CODE_REQUIRED")
        String code,

        @Schema(description = "Notification delivery channel.", example = "SMS")
        @NotBlank(message = "CHANNEL_REQUIRED")
        String channel,

        @Schema(description = "ISO 2-letter language code.", example = "ES")
        @NotBlank(message = "LANGUAGE_REQUIRED")
        @Size(min = 2, max = 2, message = "LANGUAGE_INVALID")
        String language,

        @Schema(description = "Optional email or push notification subject line.", example = "Your Taxi is on the way!")
        String subject,

        @Schema(description = "Template message body with placeholders.", example = "Hello {driverName}, trip {tripId} is ready.")
        @NotBlank(message = "BODY_REQUIRED")
        String body
) {}
