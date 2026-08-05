package com.taxai.api.booking.application.port.in;

import java.util.UUID;

public interface RemovePetUseCase {
    void execute(UUID bookingId, UUID petId);
}
