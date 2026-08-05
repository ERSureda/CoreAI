package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddLuggageHttpRequest(
        @Schema(description = "Associated booking identifier.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "BOOKING-ID_REQUIRED")
        UUID bookingId,

        @Schema(description = "Luggage item type category.", example = "SUITCASE_LARGE")
        @NotBlank(message = "TYPE_REQUIRED")
        String type,

        @Schema(description = "Quantity of items.", example = "2")
        @NotNull(message = "QUANTITY_REQUIRED")
        @Min(value = 1, message = "QUANTITY_INVALID")
        Integer quantity,

        @Schema(description = "Optional handling instructions or notes.", example = "Fragile contents")
        String notes
) {}
