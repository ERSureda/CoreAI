package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.VehicleAssignmentResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateVehicleAssignmentHttpRequest;
import java.util.UUID;

public interface CreateVehicleAssignmentUseCase {
    VehicleAssignmentResult execute(UUID driverId, CreateVehicleAssignmentHttpRequest request);
}
