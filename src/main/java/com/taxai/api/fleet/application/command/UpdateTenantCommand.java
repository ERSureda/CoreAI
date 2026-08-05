package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateTenantCommand(
        UUID id,
        String name,
        Boolean isActive
) {
    public UpdateTenantCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateTenantCommand.class.getSimpleName());
    }
}
