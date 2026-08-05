package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.ListOperatorsCommand;
import com.taxai.api.fleet.application.result.ListOperatorsResult;

public interface ListOperatorsUseCase {
    ListOperatorsResult execute(ListOperatorsCommand command);
}
