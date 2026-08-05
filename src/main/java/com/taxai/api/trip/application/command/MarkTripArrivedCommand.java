package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record MarkTripArrivedCommand(
        UUID id
) {
    public MarkTripArrivedCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(MarkTripArrivedCommand.class.getSimpleName());
    }
}
