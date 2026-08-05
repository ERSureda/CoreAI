package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record GetTripReceiptCommand(
        UUID tripId
) {
    public GetTripReceiptCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .validate(GetTripReceiptCommand.class.getSimpleName());
    }
}
