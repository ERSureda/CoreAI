package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripStatusHistoryResult;
import java.util.UUID;

public interface GetTripStatusHistoryUseCase { TripStatusHistoryResult execute(UUID id); }
