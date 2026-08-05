package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateIncidentCommand(
        UUID id,
        String priority,
        String description
) {
    public UpdateIncidentCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateIncidentCommand.class.getSimpleName());
    }
}
