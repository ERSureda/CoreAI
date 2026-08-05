package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.ResolveIncidentCommand;
import com.taxai.api.support.application.result.IncidentResult;

public interface ResolveIncidentUseCase {
    IncidentResult execute(ResolveIncidentCommand command);
}
