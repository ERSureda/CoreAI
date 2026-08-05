package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetActiveTripRouteCommand(
        UUID tripId
) {
    public GetActiveTripRouteCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .validate(GetActiveTripRouteCommand.class.getSimpleName());
    }
}
