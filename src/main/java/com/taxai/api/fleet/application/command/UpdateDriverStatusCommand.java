package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateDriverStatusCommand(
        UUID id,
        String adminStatus
) {
    public UpdateDriverStatusCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfBlank(adminStatus, "ADMIN_STATUS_REQUIRED", "Admin status is required.")
                .validate(UpdateDriverStatusCommand.class.getSimpleName());
    }
}
