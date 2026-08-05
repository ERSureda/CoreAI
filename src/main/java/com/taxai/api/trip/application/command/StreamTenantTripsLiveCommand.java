package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record StreamTenantTripsLiveCommand(
        UUID tenantId
) {
    public StreamTenantTripsLiveCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(StreamTenantTripsLiveCommand.class.getSimpleName());
    }
}
