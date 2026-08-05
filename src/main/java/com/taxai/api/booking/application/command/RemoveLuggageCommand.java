package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record RemoveLuggageCommand(
        UUID bookingId,
        UUID luggageId
) {
    public RemoveLuggageCommand {
        CommandValidator.start()
                .rejectIfNull(bookingId, "BOOKING_ID_REQUIRED", "Booking ID is required.")
                .rejectIfNull(luggageId, "LUGGAGE_ID_REQUIRED", "Luggage ID is required.")
                .validate(RemoveLuggageCommand.class.getSimpleName());
    }
}
