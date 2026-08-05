package com.taxai.api.fleet.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTenantHttpRequest(
        @Schema(description = "The registered legal name of the tenant enterprise.", example = "Taxi Central Corp")
        @NotBlank(message = "NAME_REQUIRED")
        String name,

        @Schema(description = "Tax identification number or CIF.", example = "B12345678")
        @NotBlank(message = "TAX-ID_REQUIRED")
        String taxId,

        @Schema(description = "Optional cloud data region for tenant isolation.", example = "eu-west-1")
        String dataRegion,

        @Schema(description = "Default ISO 2-letter language code.", example = "ES")
        @NotBlank(message = "DEFAULT-LANGUAGE_REQUIRED")
        @Size(min = 2, max = 2, message = "DEFAULT-LANGUAGE_INVALID")
        String defaultLanguage
) {}
