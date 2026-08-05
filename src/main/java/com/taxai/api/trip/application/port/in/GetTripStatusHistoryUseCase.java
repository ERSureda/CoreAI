package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.GetTripStatusHistoryCommand;
import com.taxai.api.trip.application.result.TripStatusHistoryResult;

public interface GetTripStatusHistoryUseCase {
    TripStatusHistoryResult execute(GetTripStatusHistoryCommand command);
}
