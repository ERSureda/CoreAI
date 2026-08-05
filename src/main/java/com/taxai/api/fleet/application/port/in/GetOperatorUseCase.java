package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.OperatorResult;
import java.util.UUID;

public interface GetOperatorUseCase {
    OperatorResult execute(UUID id);
}
