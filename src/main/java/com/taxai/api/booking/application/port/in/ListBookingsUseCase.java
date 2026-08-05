package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.ListBookingsCommand;
import com.taxai.api.booking.application.result.ListBookingsResult;

public interface ListBookingsUseCase {
    ListBookingsResult execute(ListBookingsCommand command);
}
