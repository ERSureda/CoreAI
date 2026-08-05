package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddPetHttpRequest(
        @Schema(description = "Associated booking identifier.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "BOOKING-ID_REQUIRED")
        UUID bookingId,

        @Schema(description = "Pet type or breed category.", example = "DOG_MEDIUM")
        @NotBlank(message = "TYPE_REQUIRED")
        String type,

        @Schema(description = "Quantity of pets.", example = "1")
        @NotNull(message = "QUANTITY_REQUIRED")
        @Min(value = 1, message = "QUANTITY_INVALID")
        Integer quantity,

        @Schema(description = "Flag specifying if pet is in an approved carrier.", example = "true")
        @NotNull(message = "IN-CARRIER_REQUIRED")
        Boolean inCarrier,

        @Schema(description = "Optional pet details or notes.", example = "Guide dog")
        String notes
) {}
