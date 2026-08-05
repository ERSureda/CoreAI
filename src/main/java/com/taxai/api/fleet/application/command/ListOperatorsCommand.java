package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListOperatorsCommand(
        UUID tenantId,
        String cursor,
        Integer limit
) {
    public ListOperatorsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListOperatorsCommand.class.getSimpleName());
    }
}
