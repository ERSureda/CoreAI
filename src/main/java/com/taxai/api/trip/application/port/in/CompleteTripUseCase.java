package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.CompleteTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface CompleteTripUseCase {
    TripResult execute(CompleteTripCommand command);
}
