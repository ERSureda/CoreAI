package com.taxai.api.pricing.application.result;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ReceiptResult(
        UUID id,
        UUID tripId,
        String paymentMethod,
        BigDecimal totalAmount,
        String currency,
        String driverNotes,
        Instant recordedAt
) {}
