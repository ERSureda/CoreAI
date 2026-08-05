package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.time.Instant;
import java.util.UUID;

public record ListReceiptsCommand(
        UUID tenantId,
        Instant from,
        Instant to,
        String cursor,
        Integer limit
) {
    public ListReceiptsCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .validate(ListReceiptsCommand.class.getSimpleName());
    }
}
