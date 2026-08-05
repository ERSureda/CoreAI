package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTariffCommand(
        UUID tenantId,
        String name,
        String vehicleType,
        UUID zoneId,
        BigDecimal baseFare,
        BigDecimal perKmRate,
        BigDecimal perMinRate,
        BigDecimal minimumFare
) {
    public CreateTariffCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(name, "NAME_REQUIRED", "Name is required.")
                .rejectIfBlank(vehicleType, "VEHICLE_TYPE_REQUIRED", "Vehicle type is required.")
                .rejectIfNull(baseFare, "BASE_FARE_REQUIRED", "Base fare is required.")
                .rejectIfNull(perKmRate, "PER_KM_RATE_REQUIRED", "Per km rate is required.")
                .rejectIfNull(perMinRate, "PER_MIN_RATE_REQUIRED", "Per min rate is required.")
                .rejectIfNull(minimumFare, "MINIMUM_FARE_REQUIRED", "Minimum fare is required.")
                .validate(CreateTariffCommand.class.getSimpleName());
    }
}
