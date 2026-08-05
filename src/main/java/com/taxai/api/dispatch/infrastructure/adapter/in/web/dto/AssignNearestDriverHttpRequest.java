package com.taxai.api.dispatch.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AssignNearestDriverHttpRequest(
        @Schema(description = "Trip identifier.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "TRIP-ID_REQUIRED")
        UUID tripId,

        @Schema(description = "Optional zone identifier.", example = "z1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        UUID zoneId
) {}
