package com.taxai.api.pricing.application.result;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TariffResult(
        UUID id,
        String name,
        UUID zoneId,
        String vehicleType,
        BigDecimal baseFare,
        BigDecimal perKm,
        BigDecimal perMin,
        BigDecimal minFare,
        BigDecimal waitingPerMin,
        BigDecimal nightSurchargePct,
        BigDecimal airportSurcharge,
        Instant validFrom,
        Instant validTo,
        Boolean active
) {}
