package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListVehiclesCommand(
        UUID tenantId,
        String type,
        String status,
        String cursor,
        Integer limit
) {
    public ListVehiclesCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListVehiclesCommand.class.getSimpleName());
    }
}
