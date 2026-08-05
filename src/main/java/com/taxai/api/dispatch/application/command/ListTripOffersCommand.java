package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record ListTripOffersCommand(
        UUID tripId,
        Integer wave
) {
    public ListTripOffersCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .validate(ListTripOffersCommand.class.getSimpleName());
    }
}
