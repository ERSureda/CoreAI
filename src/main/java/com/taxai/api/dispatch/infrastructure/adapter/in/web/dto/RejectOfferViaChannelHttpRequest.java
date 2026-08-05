package com.taxai.api.dispatch.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record RejectOfferViaChannelHttpRequest(
        @Schema(description = "Driver identifier.", example = "d4e5f6a7-b89c-0d1e-2f3a-4b5c6d7e8f9a")
        @NotNull(message = "DRIVER-ID_REQUIRED")
        UUID driverId,

        @Schema(description = "Offer identifier to reject.", example = "o1f2f3e4-r5a6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "OFFER-ID_REQUIRED")
        UUID offerId
) {}
