package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CancelBookingCommand(
        UUID id,
        String reason
) {
    public CancelBookingCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(CancelBookingCommand.class.getSimpleName());
    }
}
