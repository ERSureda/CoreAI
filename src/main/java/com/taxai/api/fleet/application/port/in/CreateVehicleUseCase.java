package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.CreateVehicleCommand;
import com.taxai.api.fleet.application.result.VehicleResult;

public interface CreateVehicleUseCase {
    VehicleResult execute(CreateVehicleCommand command);
}
