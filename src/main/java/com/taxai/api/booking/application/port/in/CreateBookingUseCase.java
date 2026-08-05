package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.CreateBookingCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface CreateBookingUseCase {
    BookingResult execute(CreateBookingCommand command);
}
