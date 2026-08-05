package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;

public record GetBookingByLocatorCommand(
        String locator
) {
    public GetBookingByLocatorCommand {
        CommandValidator.start()
                .rejectIfBlank(locator, "LOCATOR_REQUIRED", "Locator is required.")
                .validate(GetBookingByLocatorCommand.class.getSimpleName());
    }
}
