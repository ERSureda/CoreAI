package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.ListTripsCommand;
import com.taxai.api.trip.application.result.ListTripsResult;

public interface ListTripsUseCase {
    ListTripsResult execute(ListTripsCommand command);
}
