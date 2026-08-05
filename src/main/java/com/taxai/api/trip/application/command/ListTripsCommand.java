package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListTripsCommand(
        UUID tenantId,
        String status,
        UUID driverId,
        String cursor,
        Integer limit
) {
    public ListTripsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListTripsCommand.class.getSimpleName());
    }
}
