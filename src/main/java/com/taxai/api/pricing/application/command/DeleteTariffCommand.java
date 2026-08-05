package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record DeleteTariffCommand(
        UUID id
) {
    public DeleteTariffCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(DeleteTariffCommand.class.getSimpleName());
    }
}
