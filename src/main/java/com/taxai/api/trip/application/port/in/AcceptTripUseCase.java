package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.AcceptTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface AcceptTripUseCase {
    TripResult execute(AcceptTripCommand command);
}
