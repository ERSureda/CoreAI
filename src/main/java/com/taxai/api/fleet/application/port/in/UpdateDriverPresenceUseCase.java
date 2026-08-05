package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.DriverPresenceResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateDriverPresenceHttpRequest;
import java.util.UUID;

public interface UpdateDriverPresenceUseCase {
    DriverPresenceResult execute(UUID driverId, UpdateDriverPresenceHttpRequest request);
}
