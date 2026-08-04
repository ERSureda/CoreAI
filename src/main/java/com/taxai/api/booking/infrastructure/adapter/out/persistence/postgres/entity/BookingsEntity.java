package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.booking.domain.model.enums.BookingChannel;
import com.taxai.api.booking.domain.model.enums.BookingStatus;
import com.taxai.api.booking.domain.model.enums.BookingType;
import com.taxai.api.shared.domain.model.enums.ActorType;
import com.taxai.api.shared.domain.model.enums.VehicleType;
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
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "bookings", schema = "booking",
        uniqueConstraints = {
                @UniqueConstraint(name = "bookings_locator_key", columnNames = {"locator"})
        },
        indexes = {
                @Index(name = "ix_bookings_passenger", columnList = "passenger_id, created_at DESC"),
                @Index(name = "ix_bookings_tenant_date", columnList = "tenant_id, created_at DESC")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookingsEntity {

    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "locator", nullable = false, length = 8)
    private String locator;

    @Column(name = "passenger_id", nullable = false)
    private UUID passengerId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "channel", nullable = false)
    private BookingChannel channel;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", nullable = false)
    private BookingType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @Column(name = "scheduled_pickup_at")
    private Instant scheduledPickupAt;

    @Column(name = "pickup_timezone", nullable = false)
    private String pickupTimezone;

    @Column(name = "recurrence_rule")
    private String recurrenceRule;

    @Column(name = "recurrence_until")
    private LocalDate recurrenceUntil;

    @Column(name = "passenger_count", nullable = false)
    private Short passengerCount;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "vehicle_type_required")
    private VehicleType vehicleTypeRequired;

    @Column(name = "needs_child_seat", nullable = false)
    private Boolean needsChildSeat;

    @Column(name = "wheelchair_required", nullable = false)
    private Boolean wheelchairRequired;

    @Column(name = "notes")
    private String notes;

    @Column(name = "source_conversation_id")
    private String sourceConversationId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "created_by_actor", nullable = false)
    private ActorType createdByActor;

    @Column(name = "created_by_id")
    private UUID createdById;

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
