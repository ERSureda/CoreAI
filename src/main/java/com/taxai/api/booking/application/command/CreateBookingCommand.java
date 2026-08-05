package com.taxai.api.booking.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateBookingCommand(
        UUID tenantId,
        String channel,
        String type,
        String passengerPhone,
        Instant scheduledPickupAt,
        String recurrenceRule,
        Integer passengerCount,
        String vehicleTypeRequired,
        Boolean needsChildSeat,
        Boolean wheelchairRequired,
        String notes,
        UUID sourceConversationId,
        String idempotencyKey,
        List<AddLuggageCommand> luggage,
        List<AddPetCommand> pets
) {
    public CreateBookingCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(channel, "CHANNEL_REQUIRED", "Channel is required.")
                .rejectIfBlank(type, "TYPE_REQUIRED", "Type is required.")
                .rejectIfBlank(passengerPhone, "PASSENGER_PHONE_REQUIRED", "Passenger phone is required.")
                .rejectIfNull(passengerCount, "PASSENGER_COUNT_REQUIRED", "Passenger count is required.")
                .rejectIfBlank(idempotencyKey, "IDEMPOTENCY_KEY_REQUIRED", "Idempotency key is required.")
                .validate(CreateBookingCommand.class.getSimpleName());
    }
}
