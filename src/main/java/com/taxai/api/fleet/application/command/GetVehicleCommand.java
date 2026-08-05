package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetVehicleCommand(
        UUID id
) {
    public GetVehicleCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetVehicleCommand.class.getSimpleName());
    }
}
