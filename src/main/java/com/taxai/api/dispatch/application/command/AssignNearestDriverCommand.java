package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AssignNearestDriverCommand(
        UUID tripId,
        UUID zoneId
) {
    public AssignNearestDriverCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .validate(AssignNearestDriverCommand.class.getSimpleName());
    }
}
