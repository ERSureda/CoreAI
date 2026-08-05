package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.OperatorResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateOperatorHttpRequest;

public interface CreateOperatorUseCase {
    OperatorResult execute(CreateOperatorHttpRequest request);
}
