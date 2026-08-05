package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AddLuggageCommand(
        UUID bookingId,
        String type,
        Integer quantity,
        String notes
) {
    public AddLuggageCommand {
        CommandValidator.start()
                .rejectIfNull(bookingId, "BOOKING_ID_REQUIRED", "Booking ID is required.")
                .rejectIfBlank(type, "TYPE_REQUIRED", "Type is required.")
                .rejectIfNull(quantity, "QUANTITY_REQUIRED", "Quantity is required.")
                .validate(AddLuggageCommand.class.getSimpleName());
    }
}
