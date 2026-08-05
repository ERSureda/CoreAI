package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RemovePetHttpRequest(
        @Schema(description = "Associated booking identifier.", example = "b1c2d3e4-f5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "BOOKING-ID_REQUIRED")
        UUID bookingId,

        @Schema(description = "Pet item identifier to remove.", example = "p1e2t3i4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        @NotNull(message = "PET-ID_REQUIRED")
        UUID petId
) {}
