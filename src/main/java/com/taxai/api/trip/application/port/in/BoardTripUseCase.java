package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.BoardTripCommand;
import com.taxai.api.trip.application.result.TripResult;

public interface BoardTripUseCase {
    TripResult execute(BoardTripCommand command);
}
