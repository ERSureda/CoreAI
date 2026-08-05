package com.taxai.api.audit.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetAuditLogHttpRequest(
        @Schema(description = "Unique identifier of the audit log entry.", example = "a1u2d3i4-t5l6-7o8g-9i0d-1e2f3a4b5c6d")
        @NotNull(message = "ID_REQUIRED")
        UUID id
) {}
