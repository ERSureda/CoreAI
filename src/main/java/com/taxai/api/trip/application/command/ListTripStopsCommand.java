package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListTripStopsCommand(
        UUID tripId
) {
    public ListTripStopsCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .validate(ListTripStopsCommand.class.getSimpleName());
    }
}
