package com.taxai.api.trip.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CancelTripHttpRequest(
        @Schema(description = "Unique identifier of the trip.", example = "t1r2i3p4-a5b6-7c8d-9e0f-1a2b3c4d5e6f")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Actor type initiating cancellation.", example = "PASSENGER")
        @NotBlank(message = "CANCELLED-BY_REQUIRED")
        String cancelledBy,

        @Schema(description = "Reason code for cancellation.", example = "CHANGE_OF_PLANS")
        @NotBlank(message = "CANCEL-REASON_REQUIRED")
        String cancelReason,

        @Schema(description = "Optional additional cancellation notes.", example = "Passenger requested cancellation via call")
        String cancelNote
) {}
