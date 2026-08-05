package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateOperatorCommand(
        UUID tenantId,
        UUID userId,
        String fullName,
        String role
) {
    public CreateOperatorCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfNull(userId, "USER_ID_REQUIRED", "User ID is required.")
                .rejectIfBlank(fullName, "FULL_NAME_REQUIRED", "Full name is required.")
                .rejectIfBlank(role, "ROLE_REQUIRED", "Role is required.")
                .validate(CreateOperatorCommand.class.getSimpleName());
    }
}
