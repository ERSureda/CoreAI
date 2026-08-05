package com.taxai.api.audit.application.port.in;

import com.taxai.api.audit.application.result.*;
import com.taxai.api.audit.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface ListAuditLogsUseCase { ListAuditLogsResult execute(ListAuditLogsHttpRequest request); }
