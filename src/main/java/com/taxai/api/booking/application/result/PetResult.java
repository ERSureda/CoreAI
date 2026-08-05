package com.taxai.api.booking.application.result;

import java.util.UUID;

public record PetResult(
        UUID id,
        String type,
        Integer quantity,
        Boolean inCarrier,
        String notes
) {}
