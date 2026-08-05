package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AddPetCommand(
        UUID bookingId,
        String type,
        Integer quantity,
        Boolean inCarrier,
        String notes
) {
    public AddPetCommand {
        CommandValidator.start()
                .rejectIfNull(bookingId, "BOOKING_ID_REQUIRED", "Booking ID is required.")
                .rejectIfBlank(type, "TYPE_REQUIRED", "Type is required.")
                .rejectIfNull(quantity, "QUANTITY_REQUIRED", "Quantity is required.")
                .rejectIfNull(inCarrier, "IN_CARRIER_REQUIRED", "In carrier is required.")
                .validate(AddPetCommand.class.getSimpleName());
    }
}
