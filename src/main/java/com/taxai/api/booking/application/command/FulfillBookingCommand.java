package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record FulfillBookingCommand(
        UUID id
) {
    public FulfillBookingCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(FulfillBookingCommand.class.getSimpleName());
    }
}
