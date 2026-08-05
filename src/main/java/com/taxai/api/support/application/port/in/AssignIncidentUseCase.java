package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.AssignIncidentCommand;
import com.taxai.api.support.application.result.IncidentResult;

public interface AssignIncidentUseCase {
    IncidentResult execute(AssignIncidentCommand command);
}
