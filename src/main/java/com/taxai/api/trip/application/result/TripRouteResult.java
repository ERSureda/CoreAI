package com.taxai.api.trip.application.result;

import java.time.Instant;
import java.util.UUID;

public record TripRouteResult(
        UUID id,
        UUID tripId,
        Integer routeVersion,
        String reason,
        String provider,
        String encodedPolyline,
        Integer distanceM,
        Integer durationS,
        Integer durationTrafficS,
        String waypoints,
        Instant computedAt
) {}
