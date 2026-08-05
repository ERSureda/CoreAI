package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.LuggageResult;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.AddLuggageHttpRequest;
import java.util.UUID;

public interface AddLuggageUseCase {
    LuggageResult execute(UUID bookingId, AddLuggageHttpRequest request);
}
