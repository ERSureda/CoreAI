package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record MarkTripArrivingCommand(
        UUID id
) {
    public MarkTripArrivingCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(MarkTripArrivingCommand.class.getSimpleName());
    }
}
