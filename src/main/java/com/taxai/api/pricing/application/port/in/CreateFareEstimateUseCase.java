package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.CreateFareEstimateCommand;
import com.taxai.api.pricing.application.result.FareEstimateResult;

public interface CreateFareEstimateUseCase {
    FareEstimateResult execute(CreateFareEstimateCommand command);
}
