package com.taxai.api.pricing.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class FareEstimate extends AggregateRoot<UUID> {

    private final UUID bookingId;
    private final UUID tariffId;
    private final BigDecimal estimatedMin;
    private final BigDecimal estimatedMax;
    private final String currency;
    private final Instant calculatedAt;

    /// --- Constructors ---
    private FareEstimate(
            UUID id,
            UUID bookingId,
            UUID tariffId,
            BigDecimal estimatedMin,
            BigDecimal estimatedMax,
            String currency,
            Instant calculatedAt
    ) {
        this.id = id;
        this.bookingId = bookingId;
        this.tariffId = tariffId;
        this.estimatedMin = estimatedMin;
        this.estimatedMax = estimatedMax;
        this.currency = currency;
        this.calculatedAt = calculatedAt;

       // this.validateData();
    }

    public static FareEstimate create(
            UUID id,
            UUID bookingId,
            UUID tariffId,
            BigDecimal estimatedMin,
            BigDecimal estimatedMax,
            String currency
    ) {
        return new FareEstimate(
                id,
                bookingId,
                tariffId,
                estimatedMin,
                estimatedMax,
                currency,
                Instant.now()
        );
    }

    public static FareEstimate reconstruct(
            UUID id,
            UUID bookingId,
            UUID tariffId,
            BigDecimal estimatedMin,
            BigDecimal estimatedMax,
            String currency,
            Instant calculatedAt
    ) {
        return new FareEstimate(
                id,
                bookingId,
                tariffId,
                estimatedMin,
                estimatedMax,
                currency,
                calculatedAt
        );
    }

    /// --- Getters ---
    public UUID getBookingId() {
        return bookingId;
    }

    public UUID getTariffId() {
        return tariffId;
    }

    public BigDecimal getEstimatedMin() {
        return estimatedMin;
    }

    public BigDecimal getEstimatedMax() {
        return estimatedMax;
    }

    public String getCurrency() {
        return currency;
    }

    public Instant getCalculatedAt() {
        return calculatedAt;
    }

    /// --- Business Logic ---
}
