package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record RejectOfferViaChannelCommand(
        UUID driverId,
        UUID offerId
) {
    public RejectOfferViaChannelCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .rejectIfNull(offerId, "OFFER_ID_REQUIRED", "Offer ID is required.")
                .validate(RejectOfferViaChannelCommand.class.getSimpleName());
    }
}
