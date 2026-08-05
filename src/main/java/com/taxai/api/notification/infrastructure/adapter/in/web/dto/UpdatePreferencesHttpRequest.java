package com.taxai.api.notification.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdatePreferencesHttpRequest(
        @Schema(description = "Owner entity type.", example = "DRIVER")
        @NotBlank(message = "OWNER-TYPE_REQUIRED")
        String ownerType,

        @Schema(description = "Owner entity identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "OWNER-ID_REQUIRED")
        UUID ownerId,

        @Schema(description = "Notification channel.", example = "PUSH")
        @NotBlank(message = "CHANNEL_REQUIRED")
        String channel,

        @Schema(description = "Enabled preference flag.", example = "true")
        @NotNull(message = "ENABLED_REQUIRED")
        Boolean enabled,

        @Schema(description = "Optional quiet period start time (HH:mm).", example = "22:00")
        String quietFrom,

        @Schema(description = "Optional quiet period end time (HH:mm).", example = "07:00")
        String quietTo,

        @Schema(description = "User timezone identifier.", example = "Europe/Madrid")
        @NotBlank(message = "TIMEZONE_REQUIRED")
        String timezone
) {}
