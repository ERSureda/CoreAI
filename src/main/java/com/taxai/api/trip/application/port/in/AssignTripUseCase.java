package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.AssignTripHttpRequest;
import java.util.UUID;

public interface AssignTripUseCase { TripResult execute(UUID id, AssignTripHttpRequest request); }
