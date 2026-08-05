package com.taxai.api.booking.application.result;

import java.time.Instant;
import java.util.UUID;

public record BookingItem(
        UUID id,
        String locator,
        String status,
        String type,
        Instant createdAt
) {}
