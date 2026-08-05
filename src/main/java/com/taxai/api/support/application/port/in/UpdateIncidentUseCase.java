package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.UpdateIncidentCommand;
import com.taxai.api.support.application.result.IncidentResult;

public interface UpdateIncidentUseCase {
    IncidentResult execute(UpdateIncidentCommand command);
}
