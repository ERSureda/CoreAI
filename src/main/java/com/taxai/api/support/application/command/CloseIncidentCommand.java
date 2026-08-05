package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CloseIncidentCommand(
        UUID id
) {
    public CloseIncidentCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(CloseIncidentCommand.class.getSimpleName());
    }
}
