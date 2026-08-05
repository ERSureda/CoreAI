package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record EndVehicleAssignmentCommand(
        UUID id
) {
    public EndVehicleAssignmentCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(EndVehicleAssignmentCommand.class.getSimpleName());
    }
}
