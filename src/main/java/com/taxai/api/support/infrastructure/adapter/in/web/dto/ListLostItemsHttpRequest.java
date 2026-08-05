package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record ListLostItemsHttpRequest(
        @Schema(description = "Optional trip identifier filter.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        UUID tripId,

        @Schema(description = "Optional lost item status filter.", example = "STORED")
        String status,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
