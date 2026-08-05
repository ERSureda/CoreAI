package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.CancelBookingCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface CancelBookingUseCase {
    BookingResult execute(CancelBookingCommand command);
}
