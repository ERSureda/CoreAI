package com.taxai.api.pricing.application.result;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ReceiptItem(
        UUID tripId,
        String paymentMethod,
        BigDecimal totalAmount,
        Instant recordedAt
) {}
