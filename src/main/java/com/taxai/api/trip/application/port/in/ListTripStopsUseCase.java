package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.ListTripStopsResult;
import java.util.UUID;

public interface ListTripStopsUseCase { ListTripStopsResult execute(UUID tripId); }
