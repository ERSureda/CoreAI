package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.GetBookingByLocatorCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface GetBookingByLocatorUseCase {
    BookingResult execute(GetBookingByLocatorCommand command);
}
