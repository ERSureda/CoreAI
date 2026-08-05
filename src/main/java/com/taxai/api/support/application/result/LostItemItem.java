package com.taxai.api.support.application.result;

import java.time.Instant;
import java.util.UUID;

public record LostItemItem(
        UUID id,
        String description,
        String status,
        Instant createdAt
) {}
