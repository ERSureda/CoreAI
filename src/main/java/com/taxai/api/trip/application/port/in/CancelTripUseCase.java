package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.CancelTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface CancelTripUseCase {
    TripResult execute(CancelTripCommand command);
}
