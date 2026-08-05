package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.UpdateTripStopCommand;
import com.taxai.api.trip.application.result.TripStopResult;

public interface UpdateTripStopUseCase {
    TripStopResult execute(UpdateTripStopCommand command);
}
