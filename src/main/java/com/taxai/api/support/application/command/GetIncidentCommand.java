package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetIncidentCommand(
        UUID id
) {
    public GetIncidentCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetIncidentCommand.class.getSimpleName());
    }
}
