package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.UpdateDriverLocationCommand;
import com.taxai.api.dispatch.application.result.DriverLocationResult;

public interface UpdateDriverLocationUseCase {
    DriverLocationResult execute(UpdateDriverLocationCommand command);
}
