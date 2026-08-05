package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.FailTripHttpRequest;
import java.util.UUID;

public interface FailTripUseCase { TripResult execute(UUID id, FailTripHttpRequest request); }
