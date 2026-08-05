package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.time.Instant;
import java.util.UUID;

public record CreateFareEstimateCommand(
        UUID tenantId,
        UUID bookingId,
        String vehicleTypeRequired,
        Instant scheduledAt
) {
    public CreateFareEstimateCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(vehicleTypeRequired, "VEHICLE_TYPE_REQUIRED_REQUIRED", "Vehicle type required is required.")
                .validate(CreateFareEstimateCommand.class.getSimpleName());
    }
}
