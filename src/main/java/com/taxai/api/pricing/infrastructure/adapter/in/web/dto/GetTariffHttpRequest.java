package com.taxai.api.pricing.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record GetTariffHttpRequest(
        @Schema(description = "Unique identifier of the tariff.", example = "f1a2r3i4-f5f6-7b8c-9d0e-1f2a3b4c5d6e")
        @NotNull(message = "ID_REQUIRED")
        UUID id
) {}
