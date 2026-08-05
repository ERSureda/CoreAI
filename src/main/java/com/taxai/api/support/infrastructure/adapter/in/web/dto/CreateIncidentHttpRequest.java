package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CreateIncidentHttpRequest(
        @Schema(description = "Optional associated trip identifier.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        UUID tripId,

        @Schema(description = "Incident type category.", example = "PAYMENT_ISSUE")
        @NotBlank(message = "TYPE_REQUIRED")
        String type,

        @Schema(description = "Optional priority level.", example = "HIGH")
        String priority,

        @Schema(description = "Role or entity type reporting the incident.", example = "PASSENGER")
        @NotBlank(message = "REPORTED-BY_REQUIRED")
        String reportedBy,

        @Schema(description = "Optional reporter user identifier.", example = "u1s2e3r4-i5d6-7a8b-9c0d-1e2f3a4b5c6d")
        UUID reporterId,

        @Schema(description = "Short incident title.", example = "Incorrect fare charged")
        @NotBlank(message = "TITLE_REQUIRED")
        String title,

        @Schema(description = "Optional detailed description of the incident.", example = "Driver charged extra cash fee despite in-app payment")
        String description
) {}
