package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.AddTripStopCommand;
import com.taxai.api.trip.application.result.TripStopResult;

public interface AddTripStopUseCase {
    TripStopResult execute(AddTripStopCommand command);
}
