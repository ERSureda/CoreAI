package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateDriverPresenceCommand;
import com.taxai.api.fleet.application.result.DriverPresenceResult;

public interface UpdateDriverPresenceUseCase {
    DriverPresenceResult execute(UpdateDriverPresenceCommand command);
}
