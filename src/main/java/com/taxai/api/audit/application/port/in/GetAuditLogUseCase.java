package com.taxai.api.audit.application.port.in;

import com.taxai.api.audit.application.result.AuditLogResult;
import java.util.UUID;

public interface GetAuditLogUseCase { AuditLogResult execute(UUID id); }
