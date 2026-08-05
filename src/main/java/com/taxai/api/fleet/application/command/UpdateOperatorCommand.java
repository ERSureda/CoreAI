package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateOperatorCommand(
        UUID id,
        String role,
        Boolean isActive
) {
    public UpdateOperatorCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateOperatorCommand.class.getSimpleName());
    }
}
