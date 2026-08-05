package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.VehicleResult;
import java.util.UUID;

public interface GetVehicleUseCase {
    VehicleResult execute(UUID id);
}
