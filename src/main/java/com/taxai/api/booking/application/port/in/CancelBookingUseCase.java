package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.BookingResult;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.CancelBookingHttpRequest;
import java.util.UUID;

public interface CancelBookingUseCase {
    BookingResult execute(UUID id, CancelBookingHttpRequest request);
}
