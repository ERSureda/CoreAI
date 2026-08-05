package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.VehicleResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateVehicleHttpRequest;

public interface CreateVehicleUseCase {
    VehicleResult execute(CreateVehicleHttpRequest request);
}
