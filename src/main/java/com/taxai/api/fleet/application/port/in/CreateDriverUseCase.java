package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.DriverResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateDriverHttpRequest;

public interface CreateDriverUseCase {
    DriverResult execute(CreateDriverHttpRequest request);
}
