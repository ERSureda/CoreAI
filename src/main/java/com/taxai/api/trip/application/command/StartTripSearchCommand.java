package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record StartTripSearchCommand(
        UUID id
) {
    public StartTripSearchCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(StartTripSearchCommand.class.getSimpleName());
    }
}
