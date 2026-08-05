package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetTariffCommand(
        UUID id
) {
    public GetTariffCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetTariffCommand.class.getSimpleName());
    }
}
