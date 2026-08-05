package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateDriverPresenceHttpRequest(
        @Schema(description = "Driver identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "DRIVER-ID_REQUIRED")
        UUID driverId,

        @Schema(description = "Presence status.", example = "AVAILABLE")
        @NotBlank(message = "STATUS_REQUIRED")
        String status,

        @Schema(description = "Optional zone identifier.", example = "z1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        UUID zoneId
) {}
