package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTripReceiptHttpRequest(
        @Schema(description = "Trip identifier.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "TRIP-ID_REQUIRED")
        UUID tripId,

        @Schema(description = "Payment method used.", example = "CREDIT_CARD")
        @NotBlank(message = "PAYMENT-METHOD_REQUIRED")
        String paymentMethod,

        @Schema(description = "Total amount charged.", example = "25.50")
        @NotNull(message = "TOTAL-AMOUNT_REQUIRED")
        @DecimalMin(value = "0.00", message = "TOTAL-AMOUNT_INVALID")
        BigDecimal totalAmount,

        @Schema(description = "ISO currency code.", example = "EUR")
        @NotBlank(message = "CURRENCY_REQUIRED")
        String currency,

        @Schema(description = "Optional driver notes for receipt.", example = "Toll fees included")
        String driverNotes
) {}
