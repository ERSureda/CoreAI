package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetCurrentVehicleAssignmentCommand(
        UUID driverId
) {
    public GetCurrentVehicleAssignmentCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .validate(GetCurrentVehicleAssignmentCommand.class.getSimpleName());
    }
}
