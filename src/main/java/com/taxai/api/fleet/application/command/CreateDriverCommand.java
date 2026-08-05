package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateDriverCommand(
        UUID tenantId,
        UUID userId,
        String employeeCode,
        String fullName,
        String phone
) {
    public CreateDriverCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(employeeCode, "EMPLOYEE_CODE_REQUIRED", "Employee code is required.")
                .rejectIfBlank(fullName, "FULL_NAME_REQUIRED", "Full name is required.")
                .rejectIfBlank(phone, "PHONE_REQUIRED", "Phone is required.")
                .validate(CreateDriverCommand.class.getSimpleName());
    }
}
