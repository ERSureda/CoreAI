package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateDriverStatusCommand;
import com.taxai.api.fleet.application.result.DriverResult;

public interface UpdateDriverStatusUseCase {
    DriverResult execute(UpdateDriverStatusCommand command);
}
