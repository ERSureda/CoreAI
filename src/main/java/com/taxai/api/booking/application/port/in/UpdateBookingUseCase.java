package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.BookingResult;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.UpdateBookingHttpRequest;
import java.util.UUID;

public interface UpdateBookingUseCase {
    BookingResult execute(UUID id, UpdateBookingHttpRequest request);
}
