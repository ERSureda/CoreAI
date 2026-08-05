package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.CancelTripHttpRequest;
import java.util.UUID;

public interface CancelTripUseCase { TripResult execute(UUID id, CancelTripHttpRequest request); }
