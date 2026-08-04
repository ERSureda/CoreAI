package com.taxai.api.trip.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.ActorType;
import com.taxai.api.trip.domain.model.enums.CancelReason;
import com.taxai.api.trip.domain.model.enums.TripStatus;

import java.time.Instant;
import java.util.UUID;

public class Trip extends AggregateRoot<UUID> {

    private final UUID tenantId;
    private final UUID bookingId;
    private final UUID passengerId;
    private UUID driverId;
    private UUID vehicleId;
    private TripStatus status;
    private final UUID replacesTripId;
    private final Instant scheduledPickupAt;
    private Instant searchStartedAt;
    private Instant searchExpiresAt;
    private Instant assignedAt;
    private Instant acceptedAt;
    private Instant arrivingStartedAt;
    private Instant waitingStartedAt;
    private Instant waitDeadlineAt;
    private Instant boardedAt;
    private Instant completedAt;
    private Instant cancelledAt;
    private ActorType cancelledBy;
    private CancelReason cancelReason;
    private String cancelNote;
    private final Integer estimatedDistanceM;
    private final Integer estimatedDurationS;
    private Integer actualDistanceM;
    private Integer actualDurationS;
    private Short activeRouteVersion;
    private final Integer version;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Trip(
            UUID id,
            UUID tenantId,
            UUID bookingId,
            UUID passengerId,
            UUID driverId,
            UUID vehicleId,
            TripStatus status,
            UUID replacesTripId,
            Instant scheduledPickupAt,
            Instant searchStartedAt,
            Instant searchExpiresAt,
            Instant assignedAt,
            Instant acceptedAt,
            Instant arrivingStartedAt,
            Instant waitingStartedAt,
            Instant waitDeadlineAt,
            Instant boardedAt,
            Instant completedAt,
            Instant cancelledAt,
            ActorType cancelledBy,
            CancelReason cancelReason,
            String cancelNote,
            Integer estimatedDistanceM,
            Integer estimatedDurationS,
            Integer actualDistanceM,
            Integer actualDurationS,
            Short activeRouteVersion,
            Integer version,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.status = status;
        this.replacesTripId = replacesTripId;
        this.scheduledPickupAt = scheduledPickupAt;
        this.searchStartedAt = searchStartedAt;
        this.searchExpiresAt = searchExpiresAt;
        this.assignedAt = assignedAt;
        this.acceptedAt = acceptedAt;
        this.arrivingStartedAt = arrivingStartedAt;
        this.waitingStartedAt = waitingStartedAt;
        this.waitDeadlineAt = waitDeadlineAt;
        this.boardedAt = boardedAt;
        this.completedAt = completedAt;
        this.cancelledAt = cancelledAt;
        this.cancelledBy = cancelledBy;
        this.cancelReason = cancelReason;
        this.cancelNote = cancelNote;
        this.estimatedDistanceM = estimatedDistanceM;
        this.estimatedDurationS = estimatedDurationS;
        this.actualDistanceM = actualDistanceM;
        this.actualDurationS = actualDurationS;
        this.activeRouteVersion = activeRouteVersion;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Trip create(
            UUID id,
            UUID tenantId,
            UUID bookingId,
            UUID passengerId,
            UUID replacesTripId,
            Instant scheduledPickupAt,
            Integer estimatedDistanceM,
            Integer estimatedDurationS
    ) {
        Instant now = Instant.now();
        return new Trip(
                id,
                tenantId,
                bookingId,
                passengerId,
                null,
                null,
                TripStatus.REQUESTED,
                replacesTripId,
                scheduledPickupAt,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                estimatedDistanceM,
                estimatedDurationS,
                null,
                null,
                (short) 0,
                0,
                now,
                now
        );
    }

    public static Trip reconstruct(
            UUID id,
            UUID tenantId,
            UUID bookingId,
            UUID passengerId,
            UUID driverId,
            UUID vehicleId,
            TripStatus status,
            UUID replacesTripId,
            Instant scheduledPickupAt,
            Instant searchStartedAt,
            Instant searchExpiresAt,
            Instant assignedAt,
            Instant acceptedAt,
            Instant arrivingStartedAt,
            Instant waitingStartedAt,
            Instant waitDeadlineAt,
            Instant boardedAt,
            Instant completedAt,
            Instant cancelledAt,
            ActorType cancelledBy,
            CancelReason cancelReason,
            String cancelNote,
            Integer estimatedDistanceM,
            Integer estimatedDurationS,
            Integer actualDistanceM,
            Integer actualDurationS,
            Short activeRouteVersion,
            Integer version,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Trip(
                id,
                tenantId,
                bookingId,
                passengerId,
                driverId,
                vehicleId,
                status,
                replacesTripId,
                scheduledPickupAt,
                searchStartedAt,
                searchExpiresAt,
                assignedAt,
                acceptedAt,
                arrivingStartedAt,
                waitingStartedAt,
                waitDeadlineAt,
                boardedAt,
                completedAt,
                cancelledAt,
                cancelledBy,
                cancelReason,
                cancelNote,
                estimatedDistanceM,
                estimatedDurationS,
                actualDistanceM,
                actualDurationS,
                activeRouteVersion,
                version,
                createdAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public TripStatus getStatus() {
        return status;
    }

    public UUID getReplacesTripId() {
        return replacesTripId;
    }

    public Instant getScheduledPickupAt() {
        return scheduledPickupAt;
    }

    public Instant getSearchStartedAt() {
        return searchStartedAt;
    }

    public Instant getSearchExpiresAt() {
        return searchExpiresAt;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public Instant getAcceptedAt() {
        return acceptedAt;
    }

    public Instant getArrivingStartedAt() {
        return arrivingStartedAt;
    }

    public Instant getWaitingStartedAt() {
        return waitingStartedAt;
    }

    public Instant getWaitDeadlineAt() {
        return waitDeadlineAt;
    }

    public Instant getBoardedAt() {
        return boardedAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public ActorType getCancelledBy() {
        return cancelledBy;
    }

    public CancelReason getCancelReason() {
        return cancelReason;
    }

    public String getCancelNote() {
        return cancelNote;
    }

    public Integer getEstimatedDistanceM() {
        return estimatedDistanceM;
    }

    public Integer getEstimatedDurationS() {
        return estimatedDurationS;
    }

    public Integer getActualDistanceM() {
        return actualDistanceM;
    }

    public Integer getActualDurationS() {
        return actualDurationS;
    }

    public Short getActiveRouteVersion() {
        return activeRouteVersion;
    }

    public Integer getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
