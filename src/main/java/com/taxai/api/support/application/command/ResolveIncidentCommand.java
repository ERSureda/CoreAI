package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ResolveIncidentCommand(
        UUID id,
        String resolutionNotes
) {
    public ResolveIncidentCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfBlank(resolutionNotes, "RESOLUTION_NOTES_REQUIRED", "Resolution notes is required.")
                .validate(ResolveIncidentCommand.class.getSimpleName());
    }
}
