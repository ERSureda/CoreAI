package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.trip.domain.model.enums.StopStatus;
import com.taxai.api.trip.domain.model.enums.StopType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trip_stops", schema = "trip",
        uniqueConstraints = {
                @UniqueConstraint(name = "trip_stops_trip_id_seq_key", columnNames = {"trip_id", "seq"})
        },
        indexes = {
                @Index(name = "ix_trip_stops_trip", columnList = "trip_id, seq")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TripStopsEntity {

    @Id
    private UUID id;

    @Column(name = "trip_id", nullable = false)
    private UUID tripId;

    @Column(name = "seq", nullable = false)
    private Short seq;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", nullable = false)
    private StopType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private StopStatus status;

    @Column(name = "location_id")
    private UUID locationId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "address_snapshot", nullable = false)
    private String addressSnapshot;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "eta_at")
    private Instant etaAt;

    @Column(name = "arrived_at")
    private Instant arrivedAt;

    @Column(name = "departed_at")
    private Instant departedAt;

    @Column(name = "notes")
    private String notes;
}
