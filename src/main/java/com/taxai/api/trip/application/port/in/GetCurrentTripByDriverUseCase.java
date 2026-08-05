package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.GetCurrentTripByDriverCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface GetCurrentTripByDriverUseCase {
    TripResult execute(GetCurrentTripByDriverCommand command);
}
