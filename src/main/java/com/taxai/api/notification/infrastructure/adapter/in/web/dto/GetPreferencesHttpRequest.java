package com.taxai.api.notification.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetPreferencesHttpRequest(
        @Schema(description = "Owner entity type.", example = "DRIVER")
        @NotBlank(message = "OWNER-TYPE_REQUIRED")
        String ownerType,

        @Schema(description = "Owner entity identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "OWNER-ID_REQUIRED")
        UUID ownerId
) {}
