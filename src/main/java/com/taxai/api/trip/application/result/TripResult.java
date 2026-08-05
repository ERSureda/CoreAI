package com.taxai.api.trip.application.result;

import java.time.Instant;
import java.util.UUID;

public record TripResult(
        UUID id,
        UUID tenantId,
        UUID bookingId,
        UUID passengerId,
        UUID driverId,
        UUID vehicleId,
        String status,
        Instant scheduledPickupAt,
        Instant searchStartedAt,
        Instant searchExpiresAt,
        Instant assignedAt,
        Instant acceptedAt,
        Instant arrivingStartedAt,
        Instant waitingStartedAt,
        Instant waitDeadlineAt,
        Instant boardedAt,
        Instant completedAt,
        Instant cancelledAt,
        String cancelledBy,
        String cancelReason,
        String cancelNote,
        Integer estimatedDistanceM,
        Integer estimatedDurationS,
        Integer actualDistanceM,
        Integer actualDurationS,
        Instant createdAt,
        Instant updatedAt
) {}
