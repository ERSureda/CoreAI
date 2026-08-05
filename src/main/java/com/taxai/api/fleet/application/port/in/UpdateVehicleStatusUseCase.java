package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.VehicleResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateVehicleStatusHttpRequest;
import java.util.UUID;

public interface UpdateVehicleStatusUseCase {
    VehicleResult execute(UUID id, UpdateVehicleStatusHttpRequest request);
}
