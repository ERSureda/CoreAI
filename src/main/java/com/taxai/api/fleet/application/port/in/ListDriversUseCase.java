package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.ListDriversCommand;
import com.taxai.api.fleet.application.result.ListDriversResult;

public interface ListDriversUseCase {
    ListDriversResult execute(ListDriversCommand command);
}
