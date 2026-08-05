package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateVehicleAssignmentCommand(
        UUID driverId,
        UUID vehicleId,
        UUID createdBy
) {
    public CreateVehicleAssignmentCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .rejectIfNull(vehicleId, "VEHICLE_ID_REQUIRED", "Vehicle ID is required.")
                .rejectIfNull(createdBy, "CREATED_BY_REQUIRED", "Created by is required.")
                .validate(CreateVehicleAssignmentCommand.class.getSimpleName());
    }
}
