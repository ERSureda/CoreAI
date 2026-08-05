package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AssignTripCommand(
        UUID id,
        UUID driverId,
        UUID vehicleId
) {
    public AssignTripCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .rejectIfNull(vehicleId, "VEHICLE_ID_REQUIRED", "Vehicle ID is required.")
                .validate(AssignTripCommand.class.getSimpleName());
    }
}
