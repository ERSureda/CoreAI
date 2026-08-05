package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.CreateVehicleAssignmentCommand;
import com.taxai.api.fleet.application.result.VehicleAssignmentResult;

public interface CreateVehicleAssignmentUseCase {
    VehicleAssignmentResult execute(CreateVehicleAssignmentCommand command);
}
