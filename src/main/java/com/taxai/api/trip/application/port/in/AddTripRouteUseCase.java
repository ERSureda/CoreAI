package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.AddTripRouteCommand;
import com.taxai.api.trip.application.result.TripRouteResult;

public interface AddTripRouteUseCase {
    TripRouteResult execute(AddTripRouteCommand command);
}
