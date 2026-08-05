package com.taxai.api.pricing.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.math.BigDecimal;
import java.util.UUID;

public record CreateTripReceiptCommand(
        UUID tripId,
        BigDecimal baseFareAmount,
        BigDecimal distanceAmount,
        BigDecimal durationAmount,
        BigDecimal surchargesAmount,
        BigDecimal totalAmount,
        String paymentMethod
) {
    public CreateTripReceiptCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .rejectIfNull(totalAmount, "TOTAL_AMOUNT_REQUIRED", "Total amount is required.")
                .rejectIfBlank(paymentMethod, "PAYMENT_METHOD_REQUIRED", "Payment method is required.")
                .validate(CreateTripReceiptCommand.class.getSimpleName());
    }
}
