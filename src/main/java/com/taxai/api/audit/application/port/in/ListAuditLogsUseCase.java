package com.taxai.api.audit.application.port.in;

import com.taxai.api.audit.application.command.ListAuditLogsCommand;
import com.taxai.api.audit.application.result.ListAuditLogsResult;

public interface ListAuditLogsUseCase {
    ListAuditLogsResult execute(ListAuditLogsCommand command);
}
