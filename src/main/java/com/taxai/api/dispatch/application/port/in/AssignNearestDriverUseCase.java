package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.AssignNearestDriverCommand;
import com.taxai.api.dispatch.application.result.OfferResult;

public interface AssignNearestDriverUseCase {
    OfferResult execute(AssignNearestDriverCommand command);
}
