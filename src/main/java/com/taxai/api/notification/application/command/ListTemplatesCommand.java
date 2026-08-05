package com.taxai.api.notification.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListTemplatesCommand(
        UUID tenantId,
        String code,
        String channel,
        String language,
        String cursor,
        Integer limit
) {
    public ListTemplatesCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListTemplatesCommand.class.getSimpleName());
    }
}
