package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateTripStopHttpRequest(
        @Schema(description = "Trip identifier.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "TRIP-ID_REQUIRED")
        UUID tripId,

        @Schema(description = "Stop identifier.", example = "s1t2o3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "STOP-ID_REQUIRED")
        UUID stopId,

        @Schema(description = "New stop status.", example = "COMPLETED")
        @NotBlank(message = "STATUS_REQUIRED")
        String status
) {}
