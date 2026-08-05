package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.VehicleAssignmentResult;
import java.util.UUID;

public interface EndVehicleAssignmentUseCase {
    VehicleAssignmentResult execute(UUID id);
}
