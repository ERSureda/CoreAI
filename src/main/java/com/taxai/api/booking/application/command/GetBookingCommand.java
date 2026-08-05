package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetBookingCommand(
        UUID id
) {
    public GetBookingCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetBookingCommand.class.getSimpleName());
    }
}
