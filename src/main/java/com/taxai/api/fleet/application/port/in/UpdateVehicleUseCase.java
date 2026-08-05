package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateVehicleCommand;
import com.taxai.api.fleet.application.result.VehicleResult;

public interface UpdateVehicleUseCase {
    VehicleResult execute(UpdateVehicleCommand command);
}
