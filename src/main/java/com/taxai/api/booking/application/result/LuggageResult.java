package com.taxai.api.booking.application.result;

import java.util.UUID;

public record LuggageResult(
        UUID id,
        String type,
        Integer quantity,
        String notes
) {}
