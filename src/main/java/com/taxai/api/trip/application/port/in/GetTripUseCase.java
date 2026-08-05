package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.GetTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface GetTripUseCase {
    TripResult execute(GetTripCommand command);
}
