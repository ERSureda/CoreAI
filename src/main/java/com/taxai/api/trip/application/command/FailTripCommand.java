package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record FailTripCommand(
        UUID id,
        String reason
) {
    public FailTripCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfBlank(reason, "REASON_REQUIRED", "Reason is required.")
                .validate(FailTripCommand.class.getSimpleName());
    }
}
