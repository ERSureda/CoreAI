package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ConfirmBookingCommand(
        UUID id
) {
    public ConfirmBookingCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(ConfirmBookingCommand.class.getSimpleName());
    }
}
