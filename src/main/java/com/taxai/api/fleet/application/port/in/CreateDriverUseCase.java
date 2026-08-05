package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.CreateDriverCommand;
import com.taxai.api.fleet.application.result.DriverResult;

public interface CreateDriverUseCase {
    DriverResult execute(CreateDriverCommand command);
}
