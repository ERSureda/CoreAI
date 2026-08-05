package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateTripStopCommand(
        UUID tripId,
        UUID stopId,
        String status
) {
    public UpdateTripStopCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .rejectIfNull(stopId, "STOP_ID_REQUIRED", "Stop ID is required.")
                .rejectIfBlank(status, "STATUS_REQUIRED", "Status is required.")
                .validate(UpdateTripStopCommand.class.getSimpleName());
    }
}
