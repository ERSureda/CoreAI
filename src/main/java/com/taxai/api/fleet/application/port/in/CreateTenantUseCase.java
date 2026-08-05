package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.CreateTenantCommand;
import com.taxai.api.fleet.application.result.TenantResult;

public interface CreateTenantUseCase {
    TenantResult execute(CreateTenantCommand command);
}
