package com.taxai.api.audit.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetAuditLogCommand(
        UUID id
) {
    public GetAuditLogCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetAuditLogCommand.class.getSimpleName());
    }
}
