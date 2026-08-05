package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.AssignTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface AssignTripUseCase {
    TripResult execute(AssignTripCommand command);
}
