package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripStopResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.AddTripStopHttpRequest;
import java.util.UUID;

public interface AddTripStopUseCase { TripStopResult execute(UUID tripId, AddTripStopHttpRequest request); }
