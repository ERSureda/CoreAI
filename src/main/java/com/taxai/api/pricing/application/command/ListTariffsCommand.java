package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListTariffsCommand(
        UUID tenantId,
        UUID zoneId,
        String vehicleType,
        String cursor,
        Integer limit
) {
    public ListTariffsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListTariffsCommand.class.getSimpleName());
    }
}
