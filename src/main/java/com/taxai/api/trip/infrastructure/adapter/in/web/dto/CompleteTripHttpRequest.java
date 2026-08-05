package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CompleteTripHttpRequest(
        @Schema(description = "Unique identifier of the trip.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Actual trip distance in meters.", example = "5400")
        Integer actualDistanceM,

        @Schema(description = "Actual trip duration in seconds.", example = "720")
        Integer actualDurationS
) {}
