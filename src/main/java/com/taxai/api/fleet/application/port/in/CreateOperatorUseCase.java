package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.CreateOperatorCommand;
import com.taxai.api.fleet.application.result.OperatorResult;

public interface CreateOperatorUseCase {
    OperatorResult execute(CreateOperatorCommand command);
}
