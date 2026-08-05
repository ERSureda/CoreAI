package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.CreateTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface CreateTripUseCase {
    TripResult execute(CreateTripCommand command);
}
