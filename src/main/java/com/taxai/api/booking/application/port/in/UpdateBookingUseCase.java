package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.UpdateBookingCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface UpdateBookingUseCase {
    BookingResult execute(UpdateBookingCommand command);
}
