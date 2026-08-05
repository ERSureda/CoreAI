package com.taxai.api.support.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AssignIncidentCommand(
        UUID id,
        UUID assignedAgentId
) {
    public AssignIncidentCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfNull(assignedAgentId, "ASSIGNED_AGENT_ID_REQUIRED", "Assigned agent ID is required.")
                .validate(AssignIncidentCommand.class.getSimpleName());
    }
}
