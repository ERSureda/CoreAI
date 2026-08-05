package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetCurrentTripByDriverCommand(
        UUID driverId
) {
    public GetCurrentTripByDriverCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .validate(GetCurrentTripByDriverCommand.class.getSimpleName());
    }
}
