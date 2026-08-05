package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.ConfirmBookingCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface ConfirmBookingUseCase {
    BookingResult execute(ConfirmBookingCommand command);
}
