package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.shared.domain.model.enums.ActorType;
import com.taxai.api.trip.domain.model.enums.CancelReason;
import com.taxai.api.trip.domain.model.enums.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trips", schema = "trip",
        indexes = {
                @Index(name = "ix_trips_tenant", columnList = "tenant_id, created_at DESC")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TripEntity {

    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Column(name = "passenger_id", nullable = false)
    private UUID passengerId;

    @Column(name = "driver_id")
    private UUID driverId;

    @Column(name = "vehicle_id")
    private UUID vehicleId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private TripStatus status;

    @Column(name = "replaces_trip_id")
    private UUID replacesTripId;

    @Column(name = "scheduled_pickup_at")
    private Instant scheduledPickupAt;

    @Column(name = "search_started_at")
    private Instant searchStartedAt;

    @Column(name = "search_expires_at")
    private Instant searchExpiresAt;

    @Column(name = "assigned_at")
    private Instant assignedAt;

    @Column(name = "accepted_at")
    private Instant acceptedAt;

    @Column(name = "arriving_started_at")
    private Instant arrivingStartedAt;

    @Column(name = "waiting_started_at")
    private Instant waitingStartedAt;

    @Column(name = "wait_deadline_at")
    private Instant waitDeadlineAt;

    @Column(name = "boarded_at")
    private Instant boardedAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "cancelled_at")
    private Instant cancelledAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "cancelled_by")
    private ActorType cancelledBy;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "cancel_reason")
    private CancelReason cancelReason;

    @Column(name = "cancel_note")
    private String cancelNote;

    @Column(name = "estimated_distance_m")
    private Integer estimatedDistanceM;

    @Column(name = "estimated_duration_s")
    private Integer estimatedDurationS;

    @Column(name = "actual_distance_m")
    private Integer actualDistanceM;

    @Column(name = "actual_duration_s")
    private Integer actualDurationS;

    @Column(name = "active_route_version", nullable = false)
    private Short activeRouteVersion;

    @Version
    @Column(name = "version", nullable = false)
    private Integer version;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
