package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetOperatorCommand(
        UUID id
) {
    public GetOperatorCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetOperatorCommand.class.getSimpleName());
    }
}
