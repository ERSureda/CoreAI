package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.math.BigDecimal;
import java.util.UUID;

public record UpdateTariffCommand(
        UUID id,
        String name,
        BigDecimal baseFare,
        BigDecimal perKmRate,
        BigDecimal perMinRate,
        BigDecimal minimumFare,
        Boolean isActive
) {
    public UpdateTariffCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateTariffCommand.class.getSimpleName());
    }
}
