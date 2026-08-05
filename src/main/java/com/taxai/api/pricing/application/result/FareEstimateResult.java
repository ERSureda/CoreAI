package com.taxai.api.pricing.application.result;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record FareEstimateResult(
        UUID id,
        UUID tenantId,
        UUID bookingId,
        BigDecimal estimatedMin,
        BigDecimal estimatedMax,
        String currency,
        UUID tariffId,
        Instant calculatedAt
) {}
