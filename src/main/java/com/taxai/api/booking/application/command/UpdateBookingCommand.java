package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.time.Instant;
import java.util.UUID;

public record UpdateBookingCommand(
        UUID id,
        Instant scheduledPickupAt,
        String vehicleTypeRequired,
        String notes
) {
    public UpdateBookingCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateBookingCommand.class.getSimpleName());
    }
}
