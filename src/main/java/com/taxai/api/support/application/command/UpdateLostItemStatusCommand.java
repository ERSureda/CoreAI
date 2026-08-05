package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateLostItemStatusCommand(
        UUID id,
        String status
) {
    public UpdateLostItemStatusCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfBlank(status, "STATUS_REQUIRED", "Status is required.")
                .validate(UpdateLostItemStatusCommand.class.getSimpleName());
    }
}
