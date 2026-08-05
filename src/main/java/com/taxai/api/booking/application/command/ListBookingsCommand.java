package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListBookingsCommand(
        UUID tenantId,
        String status,
        UUID passengerId,
        String cursor,
        Integer limit
) {
    public ListBookingsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListBookingsCommand.class.getSimpleName());
    }
}
