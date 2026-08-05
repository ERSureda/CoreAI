package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.GetVehicleCommand;
import com.taxai.api.fleet.application.result.VehicleResult;

public interface GetVehicleUseCase {
    VehicleResult execute(GetVehicleCommand command);
}
