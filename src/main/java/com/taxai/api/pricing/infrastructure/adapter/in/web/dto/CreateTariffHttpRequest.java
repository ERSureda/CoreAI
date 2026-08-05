package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateTariffHttpRequest(
        @Schema(description = "Tariff name.", example = "Standard Daytime Fare")
        @NotBlank(message = "NAME_REQUIRED")
        String name,

        @Schema(description = "Optional zone identifier.", example = "z1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        UUID zoneId,

        @Schema(description = "Optional vehicle type requirement.", example = "SEDAN")
        String vehicleType,

        @Schema(description = "Base fare amount.", example = "3.50")
        @NotNull(message = "BASE-FARE_REQUIRED")
        @DecimalMin(value = "0.00", message = "BASE-FARE_INVALID")
        BigDecimal baseFare,

        @Schema(description = "Per kilometer rate.", example = "1.20")
        @NotNull(message = "PER-KM_REQUIRED")
        @DecimalMin(value = "0.00", message = "PER-KM_INVALID")
        BigDecimal perKm,

        @Schema(description = "Per minute rate.", example = "0.25")
        @NotNull(message = "PER-MIN_REQUIRED")
        @DecimalMin(value = "0.00", message = "PER-MIN_INVALID")
        BigDecimal perMin,

        @Schema(description = "Minimum fare threshold.", example = "5.00")
        @NotNull(message = "MIN-FARE_REQUIRED")
        @DecimalMin(value = "0.00", message = "MIN-FARE_INVALID")
        BigDecimal minFare,

        @Schema(description = "Optional rate per waiting minute.", example = "0.35")
        BigDecimal waitingPerMin,

        @Schema(description = "Optional night surcharge percentage.", example = "15.00")
        BigDecimal nightSurchargePct,

        @Schema(description = "Optional fixed airport surcharge.", example = "5.50")
        BigDecimal airportSurcharge,

        @Schema(description = "Validity start timestamp in UTC.", example = "2026-01-01T00:00:00Z")
        @NotNull(message = "VALID-FROM_REQUIRED")
        Instant validFrom,

        @Schema(description = "Optional validity end timestamp in UTC.", example = "2026-12-31T23:59:59Z")
        Instant validTo
) {}
