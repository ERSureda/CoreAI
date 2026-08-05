package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CancelTripCommand(
        UUID id,
        String cancelledBy,
        String cancelReason,
        String cancelNote
) {
    public CancelTripCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .rejectIfBlank(cancelledBy, "CANCELLED_BY_REQUIRED", "Cancelled by is required.")
                .rejectIfBlank(cancelReason, "CANCEL_REASON_REQUIRED", "Cancel reason is required.")
                .validate(CancelTripCommand.class.getSimpleName());
    }
}
