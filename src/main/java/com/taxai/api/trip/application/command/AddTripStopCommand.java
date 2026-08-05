package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AddTripStopCommand(
        UUID tripId,
        Integer seq,
        String type,
        UUID locationId,
        String addressSnapshot,
        String contactName,
        String contactPhone
) {
    public AddTripStopCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .rejectIfNull(seq, "SEQ_REQUIRED", "Sequence is required.")
                .rejectIfBlank(type, "TYPE_REQUIRED", "Type is required.")
                .rejectIfBlank(addressSnapshot, "ADDRESS_SNAPSHOT_REQUIRED", "Address snapshot is required.")
                .validate(AddTripStopCommand.class.getSimpleName());
    }
}
