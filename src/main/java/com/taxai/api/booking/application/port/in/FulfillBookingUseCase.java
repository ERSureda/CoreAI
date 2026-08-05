package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.BookingResult;
import java.util.UUID;

public interface FulfillBookingUseCase {
    BookingResult execute(UUID id);
}
