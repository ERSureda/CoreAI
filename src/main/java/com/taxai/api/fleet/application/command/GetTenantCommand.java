package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetTenantCommand(
        UUID id
) {
    public GetTenantCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetTenantCommand.class.getSimpleName());
    }
}
