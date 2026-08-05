package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.GetOperatorCommand;
import com.taxai.api.fleet.application.result.OperatorResult;

public interface GetOperatorUseCase {
    OperatorResult execute(GetOperatorCommand command);
}
