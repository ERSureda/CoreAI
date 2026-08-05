package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateIncidentCommand(
        UUID tenantId,
        UUID tripId,
        UUID reporterUserId,
        String reporterType,
        String category,
        String priority,
        String description
) {
    public CreateIncidentCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfNull(reporterUserId, "REPORTER_USER_ID_REQUIRED", "Reporter user ID is required.")
                .rejectIfBlank(reporterType, "REPORTER_TYPE_REQUIRED", "Reporter type is required.")
                .rejectIfBlank(category, "CATEGORY_REQUIRED", "Category is required.")
                .rejectIfBlank(priority, "PRIORITY_REQUIRED", "Priority is required.")
                .rejectIfBlank(description, "DESCRIPTION_REQUIRED", "Description is required.")
                .validate(CreateIncidentCommand.class.getSimpleName());
    }
}
