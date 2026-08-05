package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateVehicleStatusCommand(
        UUID id,
        String status
) {
    public UpdateVehicleStatusCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfBlank(status, "STATUS_REQUIRED", "Status is required.")
                .validate(UpdateVehicleStatusCommand.class.getSimpleName());
    }
}
