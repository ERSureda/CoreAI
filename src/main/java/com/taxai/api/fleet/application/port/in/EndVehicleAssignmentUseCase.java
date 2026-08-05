package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.EndVehicleAssignmentCommand;
import com.taxai.api.fleet.application.result.VehicleAssignmentResult;

public interface EndVehicleAssignmentUseCase {
    VehicleAssignmentResult execute(EndVehicleAssignmentCommand command);
}
