package com.taxai.api.audit.application.port.in;

import com.taxai.api.audit.application.command.GetAuditLogCommand;
import com.taxai.api.audit.application.result.AuditLogResult;

public interface GetAuditLogUseCase {
    AuditLogResult execute(GetAuditLogCommand command);
}
