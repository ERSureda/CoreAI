package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record BoardTripCommand(
        UUID id
) {
    public BoardTripCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(BoardTripCommand.class.getSimpleName());
    }
}
