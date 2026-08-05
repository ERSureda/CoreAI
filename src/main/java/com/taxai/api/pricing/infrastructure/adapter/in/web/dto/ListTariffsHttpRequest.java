package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record ListTariffsHttpRequest(
        @Schema(description = "Optional zone identifier filter.", example = "z1a2b3c4-d5e6-7f8a-9b0c-1d2e3f4a5b6c")
        UUID zoneId,

        @Schema(description = "Optional vehicle type filter.", example = "SEDAN")
        String vehicleType,

        @Schema(description = "Opaque pagination cursor token.", example = "eyJpZCI6IjEyMyJ9")
        String cursor,

        @Schema(description = "Maximum number of items to return.", example = "20")
        Integer limit
) {}
