package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.GetIncidentCommand;
import com.taxai.api.support.application.result.IncidentResult;

public interface GetIncidentUseCase {
    IncidentResult execute(GetIncidentCommand command);
}
