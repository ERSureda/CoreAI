package com.taxai.api.audit.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;

public record ListAuditLogsHttpRequest(
        @Schema(description = "Optional aggregate entity type filter.", example = "BOOKING")
        String aggregateType,

        @Schema(description = "Optional aggregate entity identifier filter.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        UUID aggregateId,

        @Schema(description = "Optional actor entity type filter.", example = "OPERATOR")
        String actorType,

        @Schema(description = "Optional start timestamp in UTC.", example = "2026-08-01T00:00:00Z")
        Instant from,

        @Schema(description = "Optional end timestamp in UTC.", example = "2026-08-31T23:59:59Z")
        Instant to,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
