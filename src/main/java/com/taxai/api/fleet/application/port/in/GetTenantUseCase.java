package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.GetTenantCommand;
import com.taxai.api.fleet.application.result.TenantResult;

public interface GetTenantUseCase {
    TenantResult execute(GetTenantCommand command);
}
