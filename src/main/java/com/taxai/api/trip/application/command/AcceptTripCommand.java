package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AcceptTripCommand(
        UUID id
) {
    public AcceptTripCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(AcceptTripCommand.class.getSimpleName());
    }
}
