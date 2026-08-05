package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.TenantResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateTenantHttpRequest;
import java.util.UUID;

public interface UpdateTenantUseCase {
    TenantResult execute(UUID id, UpdateTenantHttpRequest request);
}
