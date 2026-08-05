package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.CompleteTripHttpRequest;
import java.util.UUID;

public interface CompleteTripUseCase { TripResult execute(UUID id, CompleteTripHttpRequest request); }
