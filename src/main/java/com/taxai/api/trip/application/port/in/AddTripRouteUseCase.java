package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripRouteResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.AddTripRouteHttpRequest;
import java.util.UUID;

public interface AddTripRouteUseCase { TripRouteResult execute(UUID tripId, AddTripRouteHttpRequest request); }
