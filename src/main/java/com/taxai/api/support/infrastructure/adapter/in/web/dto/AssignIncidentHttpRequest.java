package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AssignIncidentHttpRequest(
        @Schema(description = "Unique identifier of the incident.", example = "i1n2c3i4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Assignee support agent identifier.", example = "a1s2s3i4-g5n6-7e8e-9i0d-1e2f3a4b5c6d")
        @NotNull(message = "ASSIGNEE-ID_REQUIRED")
        UUID assigneeId
) {}
