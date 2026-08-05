package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.CloseIncidentCommand;
import com.taxai.api.support.application.result.IncidentResult;

public interface CloseIncidentUseCase {
    IncidentResult execute(CloseIncidentCommand command);
}
