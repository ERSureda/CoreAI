package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.StartTripSearchCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface StartTripSearchUseCase {
    TripResult execute(StartTripSearchCommand command);
}
