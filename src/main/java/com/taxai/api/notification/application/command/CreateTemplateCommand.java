package com.taxai.api.notification.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateTemplateCommand(
        UUID tenantId,
        String code,
        String channel,
        String language,
        String subject,
        String bodyTemplate
) {
    public CreateTemplateCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(code, "CODE_REQUIRED", "Code is required.")
                .rejectIfBlank(channel, "CHANNEL_REQUIRED", "Channel is required.")
                .rejectIfBlank(language, "LANGUAGE_REQUIRED", "Language is required.")
                .rejectIfBlank(bodyTemplate, "BODY_TEMPLATE_REQUIRED", "Body template is required.")
                .validate(CreateTemplateCommand.class.getSimpleName());
    }
}
