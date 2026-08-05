package com.taxai.api.notification.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdatePreferencesCommand(
        String ownerType,
        UUID ownerId,
        String channel,
        Boolean enabled,
        String quietHoursStart,
        String quietHoursEnd
) {
    public UpdatePreferencesCommand {
        CommandValidator.start()
                .rejectIfBlank(ownerType, "OWNER_TYPE_REQUIRED", "Owner type is required.")
                .rejectIfNull(ownerId, "OWNER_ID_REQUIRED", "Owner ID is required.")
                .rejectIfBlank(channel, "CHANNEL_REQUIRED", "Channel is required.")
                .rejectIfNull(enabled, "ENABLED_REQUIRED", "Enabled is required.")
                .validate(UpdatePreferencesCommand.class.getSimpleName());
    }
}
