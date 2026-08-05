package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.MarkTripArrivedCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface MarkTripArrivedUseCase {
    TripResult execute(MarkTripArrivedCommand command);
}
