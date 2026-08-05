package com.taxai.api.audit.application.result;

import java.util.List;

public record ListAuditLogsResult(
        List<AuditLogResult> items,
        String nextCursor
) {}
