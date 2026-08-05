package com.taxai.api.notification.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateTemplateCommand(
        UUID id,
        String subject,
        String bodyTemplate,
        Boolean isActive
) {
    public UpdateTemplateCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateTemplateCommand.class.getSimpleName());
    }
}
