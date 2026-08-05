package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateLostItemHttpRequest(
        @Schema(description = "Trip identifier where item was lost.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "TRIP-ID_REQUIRED")
        UUID tripId,

        @Schema(description = "Optional related support incident identifier.", example = "i1n2c3i4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        UUID incidentId,

        @Schema(description = "Description of lost item.", example = "Black leather wallet with ID cards")
        @NotBlank(message = "DESCRIPTION_REQUIRED")
        String description,

        @Schema(description = "Optional storage office location.", example = "Central Depot Office - Shelf B3")
        String storageLocation,

        @Schema(description = "Optional contact phone number.", example = "+34611223344")
        String contactPhone
) {}
