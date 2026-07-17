package com.taxai.api.dispatch.domain.model;

import com.taxai.api.dispatch.domain.model.enums.OfferStatus;
import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class DispatchOffer extends AggregateRoot<UUID> {

    private final UUID tripId;
    private final UUID driverId;
    private final Short wave;
    private final Short rank;
    private final Integer distanceM;
    private final Integer etaS;
    private final Instant offeredAt;
    private final Instant expiresAt;
    private OfferStatus status;
    private Instant respondedAt;

    /// --- Constructors ---
    private DispatchOffer(
            UUID id,
            UUID tripId,
            UUID driverId,
            Short wave,
            Short rank,
            Integer distanceM,
            Integer etaS,
            Instant offeredAt,
            Instant expiresAt,
            OfferStatus status,
            Instant respondedAt
    ) {
        this.id = id;
        this.tripId = tripId;
        this.driverId = driverId;
        this.wave = wave;
        this.rank = rank;
        this.distanceM = distanceM;
        this.etaS = etaS;
        this.offeredAt = offeredAt;
        this.expiresAt = expiresAt;
        this.status = status;
        this.respondedAt = respondedAt;

        this.validateData();
    }

    public static DispatchOffer create(
            UUID id,
            UUID tripId,
            UUID driverId,
            Short wave,
            Short rank,
            Integer distanceM,
            Integer etaS,
            Instant expiresAt
    ) {
        return new DispatchOffer(
                id,
                tripId,
                driverId,
                wave,
                rank,
                distanceM,
                etaS,
                Instant.now(),
                expiresAt,
                OfferStatus.SENT,
                null
        );
    }

    public static DispatchOffer reconstruct(
            UUID id,
            UUID tripId,
            UUID driverId,
            Short wave,
            Short rank,
            Integer distanceM,
            Integer etaS,
            Instant offeredAt,
            Instant expiresAt,
            OfferStatus status,
            Instant respondedAt
    ) {
        return new DispatchOffer(
                id,
                tripId,
                driverId,
                wave,
                rank,
                distanceM,
                etaS,
                offeredAt,
                expiresAt,
                status,
                respondedAt
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public Short getWave() {
        return wave;
    }

    public Short getRank() {
        return rank;
    }

    public Integer getDistanceM() {
        return distanceM;
    }

    public Integer getEtaS() {
        return etaS;
    }

    public Instant getOfferedAt() {
        return offeredAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public Instant getRespondedAt() {
        return respondedAt;
    }

    /// --- Business Logic ---
}
