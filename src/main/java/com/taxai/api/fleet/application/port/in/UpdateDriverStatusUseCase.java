package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.DriverResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateDriverStatusHttpRequest;
import java.util.UUID;

public interface UpdateDriverStatusUseCase {
    DriverResult execute(UUID id, UpdateDriverStatusHttpRequest request);
}
