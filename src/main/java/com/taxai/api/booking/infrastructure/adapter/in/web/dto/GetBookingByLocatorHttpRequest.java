package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record GetBookingByLocatorHttpRequest(
        @Schema(description = "Alphanumeric unique locator code.", example = "TX-8921-A")
        @NotBlank(message = "LOCATOR_REQUIRED")
        String locator
) {}
