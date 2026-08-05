package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListBookingFareEstimatesCommand(
        UUID bookingId
) {
    public ListBookingFareEstimatesCommand {
        CommandValidator.start()
                .rejectIfNull(bookingId, "BOOKING_ID_REQUIRED", "Booking ID is required.")
                .validate(ListBookingFareEstimatesCommand.class.getSimpleName());
    }
}
