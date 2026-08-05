package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateDriverCommand;
import com.taxai.api.fleet.application.result.DriverResult;

public interface UpdateDriverUseCase {
    DriverResult execute(UpdateDriverCommand command);
}
