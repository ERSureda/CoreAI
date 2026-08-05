package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.ListIncidentsCommand;
import com.taxai.api.support.application.result.ListIncidentsResult;

public interface ListIncidentsUseCase {
    ListIncidentsResult execute(ListIncidentsCommand command);
}
