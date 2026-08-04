package com.taxai.api.pricing.domain.model;

import com.taxai.api.pricing.domain.model.enums.PaymentIntent;
import com.taxai.api.shared.domain.model.AggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class TripReceipt extends AggregateRoot<UUID> {

    private final UUID tripId;
    private final PaymentIntent paymentMethod;
    private final BigDecimal totalAmount;
    private final String currency;
    private final String driverNotes;
    private final Instant recordedAt;

    /// --- Constructors ---
    private TripReceipt(
            UUID id,
            UUID tripId,
            PaymentIntent paymentMethod,
            BigDecimal totalAmount,
            String currency,
            String driverNotes,
            Instant recordedAt
    ) {
        this.id = id;
        this.tripId = tripId;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.driverNotes = driverNotes;
        this.recordedAt = recordedAt;

       // this.validateData();
    }

    public static TripReceipt create(
            UUID id,
            UUID tripId,
            PaymentIntent paymentMethod,
            BigDecimal totalAmount,
            String currency,
            String driverNotes
    ) {
        return new TripReceipt(
                id,
                tripId,
                paymentMethod,
                totalAmount,
                currency,
                driverNotes,
                Instant.now()
        );
    }

    public static TripReceipt reconstruct(
            UUID id,
            UUID tripId,
            PaymentIntent paymentMethod,
            BigDecimal totalAmount,
            String currency,
            String driverNotes,
            Instant recordedAt
    ) {
        return new TripReceipt(
                id,
                tripId,
                paymentMethod,
                totalAmount,
                currency,
                driverNotes,
                recordedAt
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public PaymentIntent getPaymentMethod() {
        return paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDriverNotes() {
        return driverNotes;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    /// --- Business Logic ---
}
