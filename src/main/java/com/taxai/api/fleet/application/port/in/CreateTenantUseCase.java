package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.*;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface CreateTenantUseCase {
    TenantResult execute(CreateTenantHttpRequest request);
}
