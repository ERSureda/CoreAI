package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddTripRouteHttpRequest(
        @Schema(description = "Trip identifier.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "TRIP-ID_REQUIRED")
        UUID tripId,

        @Schema(description = "Route calculation reason.", example = "INITIAL_CALCULATION")
        @NotBlank(message = "REASON_REQUIRED")
        String reason,

        @Schema(description = "Routing engine provider.", example = "GOOGLE_MAPS")
        @NotBlank(message = "PROVIDER_REQUIRED")
        String provider,

        @Schema(description = "Encoded polyline string.", example = "a~|bFg~|aM...")
        @NotBlank(message = "ENCODED-POLYLINE_REQUIRED")
        String encodedPolyline,

        @Schema(description = "Route distance in meters.", example = "12500")
        @NotNull(message = "DISTANCE-M_REQUIRED")
        @Min(value = 0, message = "DISTANCE-M_INVALID")
        Integer distanceM,

        @Schema(description = "Route duration in seconds.", example = "900")
        @NotNull(message = "DURATION-S_REQUIRED")
        @Min(value = 0, message = "DURATION-S_INVALID")
        Integer durationS,

        @Schema(description = "Optional traffic-adjusted duration in seconds.", example = "1050")
        Integer durationTrafficS,

        @Schema(description = "Optional waypoints JSON or polyline snapshot.", example = "[{\"lat\":40.4167,\"lng\":-3.7037}]")
        String waypoints
) {}
