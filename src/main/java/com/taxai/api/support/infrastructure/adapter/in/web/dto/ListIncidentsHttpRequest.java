package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record ListIncidentsHttpRequest(
        @Schema(description = "Optional status filter.", example = "OPEN")
        String status,

        @Schema(description = "Optional priority filter.", example = "HIGH")
        String priority,

        @Schema(description = "Optional trip identifier filter.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        UUID tripId,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
