package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripRouteResult;
import java.util.UUID;

public interface GetActiveTripRouteUseCase { TripRouteResult execute(UUID tripId); }
