package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.PetResult;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.AddPetHttpRequest;
import java.util.UUID;

public interface AddPetUseCase {
    PetResult execute(UUID bookingId, AddPetHttpRequest request);
}
