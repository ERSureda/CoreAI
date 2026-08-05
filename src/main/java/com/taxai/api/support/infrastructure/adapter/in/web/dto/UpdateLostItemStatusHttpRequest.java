package com.taxai.api.support.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateLostItemStatusHttpRequest(
        @Schema(description = "Unique identifier of the lost item.", example = "l1o2s3t4-i5t6-7e8m-9i0d-1e2f3a4b5c6d")
        @NotNull(message = "ID_REQUIRED")
        UUID id,

        @Schema(description = "Updated item status.", example = "RETURNED")
        @NotBlank(message = "STATUS_REQUIRED")
        String status
) {}
