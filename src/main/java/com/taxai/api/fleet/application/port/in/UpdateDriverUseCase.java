package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.DriverResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateDriverHttpRequest;
import java.util.UUID;

public interface UpdateDriverUseCase {
    DriverResult execute(UUID id, UpdateDriverHttpRequest request);
}
