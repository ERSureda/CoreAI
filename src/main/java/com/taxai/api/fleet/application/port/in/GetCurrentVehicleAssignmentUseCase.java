package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.GetCurrentVehicleAssignmentCommand;
import com.taxai.api.fleet.application.result.VehicleAssignmentResult;

public interface GetCurrentVehicleAssignmentUseCase {
    VehicleAssignmentResult execute(GetCurrentVehicleAssignmentCommand command);
}
