package com.taxai.api.audit.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.time.Instant;
import java.util.UUID;

public record ListAuditLogsCommand(
        UUID tenantId,
        String aggregateType,
        UUID aggregateId,
        UUID actorUserId,
        Instant from,
        Instant to,
        String cursor,
        Integer limit
) {
    public ListAuditLogsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListAuditLogsCommand.class.getSimpleName());
    }
}
