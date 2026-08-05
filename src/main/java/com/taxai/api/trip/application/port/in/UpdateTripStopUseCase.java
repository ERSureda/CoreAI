package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripStopResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.UpdateTripStopHttpRequest;
import java.util.UUID;

public interface UpdateTripStopUseCase { TripStopResult execute(UUID tripId, UUID stopId, UpdateTripStopHttpRequest request); }
