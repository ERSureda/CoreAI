package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateDriverPresenceCommand(
        UUID driverId,
        String status,
        UUID zoneId
) {
    public UpdateDriverPresenceCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .rejectIfBlank(status, "STATUS_REQUIRED", "Status is required.")
                .validate(UpdateDriverPresenceCommand.class.getSimpleName());
    }
}
