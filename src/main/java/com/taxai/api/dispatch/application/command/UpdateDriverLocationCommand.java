package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateDriverLocationCommand(
        UUID driverId,
        Double lat,
        Double lng
) {
    public UpdateDriverLocationCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .rejectIfNull(lat, "LAT_REQUIRED", "Latitude is required.")
                .rejectIfNull(lng, "LNG_REQUIRED", "Longitude is required.")
                .validate(UpdateDriverLocationCommand.class.getSimpleName());
    }
}
