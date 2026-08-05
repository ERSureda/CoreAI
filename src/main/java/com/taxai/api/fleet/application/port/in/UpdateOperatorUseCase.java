package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.OperatorResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateOperatorHttpRequest;
import java.util.UUID;

public interface UpdateOperatorUseCase {
    OperatorResult execute(UUID id, UpdateOperatorHttpRequest request);
}
