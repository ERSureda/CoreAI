package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.VehicleResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateVehicleHttpRequest;
import java.util.UUID;

public interface UpdateVehicleUseCase {
    VehicleResult execute(UUID id, UpdateVehicleHttpRequest request);
}
