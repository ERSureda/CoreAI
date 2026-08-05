package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListPendingDriverOffersCommand(
        UUID driverId
) {
    public ListPendingDriverOffersCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .validate(ListPendingDriverOffersCommand.class.getSimpleName());
    }
}
