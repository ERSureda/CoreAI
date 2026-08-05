package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetLostItemCommand(
        UUID id
) {
    public GetLostItemCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(GetLostItemCommand.class.getSimpleName());
    }
}
