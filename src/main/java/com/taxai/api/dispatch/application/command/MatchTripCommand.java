package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record MatchTripCommand(
        UUID tripId
) {
    public MatchTripCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .validate(MatchTripCommand.class.getSimpleName());
    }
}
