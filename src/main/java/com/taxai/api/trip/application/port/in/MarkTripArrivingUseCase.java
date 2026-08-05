package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.MarkTripArrivingCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface MarkTripArrivingUseCase {
    TripResult execute(MarkTripArrivingCommand command);
}
