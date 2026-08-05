package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record AddTripStopHttpRequest(
        @Schema(description = "Trip identifier.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "TRIP-ID_REQUIRED")
        UUID tripId,

        @Schema(description = "Stop sequence number.", example = "1")
        @NotNull(message = "SEQ_REQUIRED")
        @Min(value = 1, message = "SEQ_INVALID")
        Integer seq,

        @Schema(description = "Stop type.", example = "PICKUP")
        @NotBlank(message = "TYPE_REQUIRED")
        String type,

        @Schema(description = "Optional saved location ID.", example = "l1o2c3a4-t5i6-7o8n-9i0d-1e2f3a4b5c6d")
        UUID locationId,

        @Schema(description = "Address snapshot string.", example = "Gran Via 45, Madrid")
        @NotBlank(message = "ADDRESS-SNAPSHOT_REQUIRED")
        String addressSnapshot,

        @Schema(description = "Optional contact name at stop.", example = "Juan Perez")
        String contactName,

        @Schema(description = "Optional contact phone at stop.", example = "+34600112233")
        String contactPhone
) {}
