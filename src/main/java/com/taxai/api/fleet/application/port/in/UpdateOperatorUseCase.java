package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.UpdateOperatorCommand;
import com.taxai.api.fleet.application.result.OperatorResult;

public interface UpdateOperatorUseCase {
    OperatorResult execute(UpdateOperatorCommand command);
}
