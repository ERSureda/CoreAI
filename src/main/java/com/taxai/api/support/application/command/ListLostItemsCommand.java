package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListLostItemsCommand(
        UUID tenantId,
        UUID tripId,
        String status,
        String cursor,
        Integer limit
) {
    public ListLostItemsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListLostItemsCommand.class.getSimpleName());
    }
}
