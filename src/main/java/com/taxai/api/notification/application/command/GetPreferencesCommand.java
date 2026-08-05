package com.taxai.api.notification.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetPreferencesCommand(
        String ownerType,
        UUID ownerId
) {
    public GetPreferencesCommand {
        CommandValidator.start()
                .rejectIfBlank(ownerType, "OWNER_TYPE_REQUIRED", "Owner type is required.")
                .rejectIfNull(ownerId, "OWNER_ID_REQUIRED", "Owner ID is required.")
                .validate(GetPreferencesCommand.class.getSimpleName());
    }
}
