package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListIncidentsCommand(
        UUID tenantId,
        String status,
        String priority,
        UUID tripId,
        String cursor,
        Integer limit
) {
    public ListIncidentsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListIncidentsCommand.class.getSimpleName());
    }
}
