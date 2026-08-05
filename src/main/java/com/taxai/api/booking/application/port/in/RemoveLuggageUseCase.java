package com.taxai.api.booking.application.port.in;

import java.util.UUID;

public interface RemoveLuggageUseCase {
    void execute(UUID bookingId, UUID luggageId);
}
