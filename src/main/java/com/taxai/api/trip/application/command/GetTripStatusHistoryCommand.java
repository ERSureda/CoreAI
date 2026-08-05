package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetTripStatusHistoryCommand(
        UUID id
) {
    public GetTripStatusHistoryCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetTripStatusHistoryCommand.class.getSimpleName());
    }
}
