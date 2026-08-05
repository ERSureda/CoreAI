package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.DriverLocationResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.UpdateDriverLocationHttpRequest;
import java.util.UUID;

public interface UpdateDriverLocationUseCase { DriverLocationResult execute(UUID driverId, UpdateDriverLocationHttpRequest request); }
