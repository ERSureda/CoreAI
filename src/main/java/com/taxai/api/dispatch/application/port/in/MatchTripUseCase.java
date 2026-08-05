package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.MatchTripCommand;
import com.taxai.api.dispatch.application.result.MatchTripResult;

public interface MatchTripUseCase {
    MatchTripResult execute(MatchTripCommand command);
}
