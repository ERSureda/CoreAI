package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.FulfillBookingCommand;
import com.taxai.api.booking.application.result.BookingResult;

public interface FulfillBookingUseCase {
    BookingResult execute(FulfillBookingCommand command);
}
