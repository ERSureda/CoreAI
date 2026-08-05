package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateDriverStatusHttpRequest(
        @Schema(description = "Unique identifier of the driver.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "New admin status.", example = "SUSPENDED")
        @NotBlank(message = "ADMIN-STATUS_REQUIRED")
        String adminStatus
) {}
