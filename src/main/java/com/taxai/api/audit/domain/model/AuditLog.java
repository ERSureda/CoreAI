package com.taxai.api.audit.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.ActorType;

import java.time.Instant;
import java.util.UUID;

public class AuditLog extends AggregateRoot<Long> {

    private final Instant occurredAt;
    private final String aggregateType;
    private final UUID aggregateId;
    private final String action;
    private final ActorType actorType;
    private final UUID actorId;
    private final String source;
    private final UUID eventId;

    /// --- Constructors ---
    private AuditLog(
            Long id,
            Instant occurredAt,
            String aggregateType,
            UUID aggregateId,
            String action,
            ActorType actorType,
            UUID actorId,
            String source,
            UUID eventId
    ) {
        this.id = id;
        this.occurredAt = occurredAt;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.action = action;
        this.actorType = actorType;
        this.actorId = actorId;
        this.source = source;
        this.eventId = eventId;

       // this.validateData();
    }

    public static AuditLog create(
            String aggregateType,
            UUID aggregateId,
            String action,
            ActorType actorType,
            UUID actorId,
            String source,
            UUID eventId
    ) {
        return new AuditLog(
                null,
                Instant.now(),
                aggregateType,
                aggregateId,
                action,
                actorType,
                actorId,
                source,
                eventId
        );
    }

    public static AuditLog reconstruct(
            Long id,
            Instant occurredAt,
            String aggregateType,
            UUID aggregateId,
            String action,
            ActorType actorType,
            UUID actorId,
            String source,
            UUID eventId
    ) {
        return new AuditLog(
                id,
                occurredAt,
                aggregateType,
                aggregateId,
                action,
                actorType,
                actorId,
                source,
                eventId
        );
    }

    /// --- Getters ---
    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public UUID getAggregateId() {
        return aggregateId;
    }

    public String getAction() {
        return action;
    }

    public ActorType getActorType() {
        return actorType;
    }

    public UUID getActorId() {
        return actorId;
    }

    public String getSource() {
        return source;
    }

    public UUID getEventId() {
        return eventId;
    }

    /// --- Business Logic ---
}
