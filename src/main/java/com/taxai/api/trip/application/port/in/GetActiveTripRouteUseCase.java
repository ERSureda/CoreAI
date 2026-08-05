package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.GetActiveTripRouteCommand;
import com.taxai.api.trip.application.result.TripRouteResult;

public interface GetActiveTripRouteUseCase {
    TripRouteResult execute(GetActiveTripRouteCommand command);
}
