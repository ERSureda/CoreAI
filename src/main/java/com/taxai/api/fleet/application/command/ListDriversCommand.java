package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListDriversCommand(
        UUID tenantId,
        String status,
        String cursor,
        Integer limit
) {
    public ListDriversCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListDriversCommand.class.getSimpleName());
    }
}
