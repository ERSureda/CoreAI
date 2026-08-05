package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.BookingResult;
import java.util.UUID;

public interface ConfirmBookingUseCase {
    BookingResult execute(UUID id);
}
