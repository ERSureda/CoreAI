package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RemoveLuggageHttpRequest(
        @Schema(description = "Associated booking identifier.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "BOOKING-ID_REQUIRED")
        UUID bookingId,

        @Schema(description = "Luggage item identifier to remove.", example = "l1u2g3g4-a5g6-7e8i-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "LUGGAGE-ID_REQUIRED")
        UUID luggageId
) {}
