package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.GetBookingCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface GetBookingUseCase {
    BookingResult execute(GetBookingCommand command);
}
