package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record UpdateTariffHttpRequest(
        @Schema(description = "Unique identifier of the tariff.", example = "f1a2r3i4-f5f6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated base fare.", example = "3.80")
        BigDecimal baseFare,

        @Schema(description = "Updated per km rate.", example = "1.25")
        BigDecimal perKm,

        @Schema(description = "Updated per min rate.", example = "0.28")
        BigDecimal perMin,

        @Schema(description = "Active status flag.", example = "true")
        Boolean active
) {}
