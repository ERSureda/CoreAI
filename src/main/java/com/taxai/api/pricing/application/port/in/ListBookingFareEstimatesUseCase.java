package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.ListBookingFareEstimatesCommand;
import com.taxai.api.pricing.application.result.ListFareEstimatesResult;

public interface ListBookingFareEstimatesUseCase {
    ListFareEstimatesResult execute(ListBookingFareEstimatesCommand command);
}
