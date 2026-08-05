package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateVehicleStatusCommand;
import com.taxai.api.fleet.application.result.VehicleResult;

public interface UpdateVehicleStatusUseCase {
    VehicleResult execute(UpdateVehicleStatusCommand command);
}
