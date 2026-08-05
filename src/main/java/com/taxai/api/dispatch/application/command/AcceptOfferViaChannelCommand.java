package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AcceptOfferViaChannelCommand(
        UUID driverId,
        UUID offerId
) {
    public AcceptOfferViaChannelCommand {
        CommandValidator.start()
                .rejectIfNull(driverId, "DRIVER_ID_REQUIRED", "Driver ID is required.")
                .rejectIfNull(offerId, "OFFER_ID_REQUIRED", "Offer ID is required.")
                .validate(AcceptOfferViaChannelCommand.class.getSimpleName());
    }
}
