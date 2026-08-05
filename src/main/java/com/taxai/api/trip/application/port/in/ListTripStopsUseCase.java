package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.ListTripStopsCommand;
import com.taxai.api.trip.application.result.ListTripStopsResult;

public interface ListTripStopsUseCase {
    ListTripStopsResult execute(ListTripStopsCommand command);
}
