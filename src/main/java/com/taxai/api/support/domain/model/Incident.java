package com.taxai.api.support.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.ActorType;
import com.taxai.api.support.domain.model.enums.IncidentStatus;
import com.taxai.api.support.domain.model.enums.IncidentType;

import java.time.Instant;
import java.util.UUID;

public class Incident extends AggregateRoot<UUID> {

    private final UUID tripId;
    private final IncidentType type;
    private IncidentStatus status;
    private final Short priority;
    private final ActorType reportedBy;
    private final UUID reporterId;
    private final UUID assigneeId;
    private final String title;
    private final String description;
    private String resolution;
    private final UUID sourceEventId;
    private final Instant createdAt;
    private Instant resolvedAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Incident(
            UUID id,
            UUID tripId,
            IncidentType type,
            IncidentStatus status,
            Short priority,
            ActorType reportedBy,
            UUID reporterId,
            UUID assigneeId,
            String title,
            String description,
            String resolution,
            UUID sourceEventId,
            Instant createdAt,
            Instant resolvedAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.tripId = tripId;
        this.type = type;
        this.status = status;
        this.priority = priority;
        this.reportedBy = reportedBy;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.title = title;
        this.description = description;
        this.resolution = resolution;
        this.sourceEventId = sourceEventId;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Incident create(
            UUID id,
            UUID tripId,
            IncidentType type,
            Short priority,
            ActorType reportedBy,
            UUID reporterId,
            UUID assigneeId,
            String title,
            String description,
            UUID sourceEventId
    ) {
        Instant now = Instant.now();
        return new Incident(
                id,
                tripId,
                type,
                IncidentStatus.OPEN,
                priority,
                reportedBy,
                reporterId,
                assigneeId,
                title,
                description,
                null,
                sourceEventId,
                now,
                null,
                now
        );
    }

    public static Incident reconstruct(
            UUID id,
            UUID tripId,
            IncidentType type,
            IncidentStatus status,
            Short priority,
            ActorType reportedBy,
            UUID reporterId,
            UUID assigneeId,
            String title,
            String description,
            String resolution,
            UUID sourceEventId,
            Instant createdAt,
            Instant resolvedAt,
            Instant updatedAt
    ) {
        return new Incident(
                id,
                tripId,
                type,
                status,
                priority,
                reportedBy,
                reporterId,
                assigneeId,
                title,
                description,
                resolution,
                sourceEventId,
                createdAt,
                resolvedAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public IncidentType getType() {
        return type;
    }

    public IncidentStatus getStatus() {
        return status;
    }

    public Short getPriority() {
        return priority;
    }

    public ActorType getReportedBy() {
        return reportedBy;
    }

    public UUID getReporterId() {
        return reporterId;
    }

    public UUID getAssigneeId() {
        return assigneeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getResolution() {
        return resolution;
    }

    public UUID getSourceEventId() {
        return sourceEventId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getResolvedAt() {
        return resolvedAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
