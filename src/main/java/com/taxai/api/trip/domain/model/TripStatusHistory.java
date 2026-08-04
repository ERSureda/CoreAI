package com.taxai.api.trip.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.ActorType;
import com.taxai.api.trip.domain.model.enums.TripStatus;

import java.time.Instant;
import java.util.UUID;

public class TripStatusHistory extends AggregateRoot<Long> {

    private final UUID tripId;
    private final TripStatus fromStatus;
    private final TripStatus toStatus;
    private final ActorType actorType;
    private final UUID actorId;
    private final String reason;
    private final UUID eventId;
    private final String metadata;
    private final Instant occurredAt;

    /// --- Constructors ---
    private TripStatusHistory(
            Long id,
            UUID tripId,
            TripStatus fromStatus,
            TripStatus toStatus,
            ActorType actorType,
            UUID actorId,
            String reason,
            UUID eventId,
            String metadata,
            Instant occurredAt
    ) {
        this.id = id;
        this.tripId = tripId;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.actorType = actorType;
        this.actorId = actorId;
        this.reason = reason;
        this.eventId = eventId;
        this.metadata = metadata;
        this.occurredAt = occurredAt;

       // this.validateData();
    }

    public static TripStatusHistory create(
            UUID tripId,
            TripStatus fromStatus,
            TripStatus toStatus,
            ActorType actorType,
            UUID actorId,
            String reason,
            UUID eventId,
            String metadata
    ) {
        return new TripStatusHistory(
                null,
                tripId,
                fromStatus,
                toStatus,
                actorType,
                actorId,
                reason,
                eventId,
                metadata,
                Instant.now()
        );
    }

    public static TripStatusHistory reconstruct(
            Long id,
            UUID tripId,
            TripStatus fromStatus,
            TripStatus toStatus,
            ActorType actorType,
            UUID actorId,
            String reason,
            UUID eventId,
            String metadata,
            Instant occurredAt
    ) {
        return new TripStatusHistory(
                id,
                tripId,
                fromStatus,
                toStatus,
                actorType,
                actorId,
                reason,
                eventId,
                metadata,
                occurredAt
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public TripStatus getFromStatus() {
        return fromStatus;
    }

    public TripStatus getToStatus() {
        return toStatus;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public UUID getActorId() {
        return actorId;
    }

    public String getReason() {
        return reason;
    }

    public UUID getEventId() {
        return eventId;
    }

    public String getMetadata() {
        return metadata;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    /// --- Business Logic ---
}
