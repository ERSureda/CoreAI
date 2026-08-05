package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.DriverResult;
import java.util.UUID;

public interface GetDriverUseCase {
    DriverResult execute(UUID id);
}
