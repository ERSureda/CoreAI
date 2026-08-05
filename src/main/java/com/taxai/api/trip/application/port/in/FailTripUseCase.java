package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.FailTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface FailTripUseCase {
    TripResult execute(FailTripCommand command);
}
