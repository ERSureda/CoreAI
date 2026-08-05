package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record RemovePetCommand(
        UUID bookingId,
        UUID petId
) {
    public RemovePetCommand {
        CommandValidator.start()
                .rejectIfNull(bookingId, "BOOKING_ID_REQUIRED", "Booking ID is required.")
                .rejectIfNull(petId, "PET_ID_REQUIRED", "Pet ID is required.")
                .validate(RemovePetCommand.class.getSimpleName());
    }
}
