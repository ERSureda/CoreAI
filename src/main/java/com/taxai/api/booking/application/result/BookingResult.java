package com.taxai.api.booking.application.result;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record BookingResult(
        UUID id,
        String locator,
        UUID tenantId,
        UUID passengerId,
        String channel,
        String type,
        String status,
        Instant scheduledPickupAt,
        Integer passengerCount,
        String vehicleTypeRequired,
        Boolean needsChildSeat,
        Boolean wheelchairRequired,
        String notes,
        UUID sourceConversationId,
        List<LuggageResult> luggage,
        List<PetResult> pets,
        Instant createdAt,
        Instant updatedAt
) {}
