package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.GetDriverCommand;
import com.taxai.api.fleet.application.result.DriverResult;

public interface GetDriverUseCase {
    DriverResult execute(GetDriverCommand command);
}
