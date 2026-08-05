package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CompleteTripCommand(
        UUID id,
        Integer actualDistanceM,
        Integer actualDurationS
) {
    public CompleteTripCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(CompleteTripCommand.class.getSimpleName());
    }
}
