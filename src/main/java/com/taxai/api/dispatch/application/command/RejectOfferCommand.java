package com.taxai.api.dispatch.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record RejectOfferCommand(
        UUID offerId
) {
    public RejectOfferCommand {
        CommandValidator.start()
                .rejectIfNull(offerId, "OFFER_ID_REQUIRED", "Offer ID is required.")
                .validate(RejectOfferCommand.class.getSimpleName());
    }
}
