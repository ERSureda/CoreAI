package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.CreateIncidentCommand;
import com.taxai.api.support.application.result.IncidentResult;

public interface CreateIncidentUseCase {
    IncidentResult execute(CreateIncidentCommand command);
}
