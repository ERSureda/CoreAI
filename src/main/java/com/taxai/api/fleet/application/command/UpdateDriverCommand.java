package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateDriverCommand(
        UUID id,
        String fullName,
        String phone
) {
    public UpdateDriverCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateDriverCommand.class.getSimpleName());
    }
}
