package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateLostItemCommand(
        UUID tenantId,
        UUID tripId,
        UUID reportedByUserId,
        String description
) {
    public CreateLostItemCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .rejectIfNull(reportedByUserId, "REPORTED_BY_USER_ID_REQUIRED", "Reported by user ID is required.")
                .rejectIfBlank(description, "DESCRIPTION_REQUIRED", "Description is required.")
                .validate(CreateLostItemCommand.class.getSimpleName());
    }
}
