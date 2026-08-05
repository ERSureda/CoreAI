package com.taxai.api.trip.application.result;

import java.time.Instant;
import java.util.UUID;

public record StatusHistoryItem(
        String fromStatus,
        String toStatus,
        String actorType,
        UUID actorId,
        String reason,
        Instant occurredAt
) {}
