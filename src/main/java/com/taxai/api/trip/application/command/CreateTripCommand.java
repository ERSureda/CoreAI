package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateTripCommand(
        UUID tenantId,
        UUID bookingId,
        UUID passengerId
) {
    public CreateTripCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfNull(bookingId, "BOOKING_ID_REQUIRED", "Booking ID is required.")
                .rejectIfNull(passengerId, "PASSENGER_ID_REQUIRED", "Passenger ID is required.")
                .validate(CreateTripCommand.class.getSimpleName());
    }
}
