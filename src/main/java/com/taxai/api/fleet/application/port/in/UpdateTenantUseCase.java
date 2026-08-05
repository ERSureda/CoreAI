package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateTenantCommand;
import com.taxai.api.fleet.application.result.TenantResult;

public interface UpdateTenantUseCase {
    TenantResult execute(UpdateTenantCommand command);
}
