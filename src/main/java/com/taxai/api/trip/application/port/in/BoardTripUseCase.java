package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.TripResult;
import java.util.UUID;

public interface BoardTripUseCase { TripResult execute(UUID id); }
