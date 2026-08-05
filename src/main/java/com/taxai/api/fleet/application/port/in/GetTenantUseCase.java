package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.TenantResult;
import java.util.UUID;

public interface GetTenantUseCase {
    TenantResult execute(UUID id);
}
