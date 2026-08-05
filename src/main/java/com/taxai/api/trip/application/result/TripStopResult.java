package com.taxai.api.trip.application.result;

import java.time.Instant;
import java.util.UUID;

public record TripStopResult(
        UUID id,
        UUID tripId,
        Integer seq,
        String type,
        String status,
        UUID locationId,
        String addressSnapshot,
        String contactName,
        String contactPhone,
        Instant etaAt,
        Instant arrivedAt,
        Instant departedAt,
        String notes
) {}
