package com.taxai.api.dispatch.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.dispatch.domain.model.enums.OfferStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "dispatch_offers", schema = "dispatch",
        uniqueConstraints = {
                @UniqueConstraint(name = "dispatch_offers_trip_id_driver_id_wave_key", columnNames = {"trip_id", "driver_id", "wave"})
        },
        indexes = {
                @Index(name = "idx_offers_trip", columnList = "trip_id, wave, rank"),
                @Index(name = "idx_offers_driver", columnList = "driver_id, offered_at DESC")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DispatchOfferEntity {

    @Id
    private UUID id;

    @Column(name = "trip_id", nullable = false)
    private UUID tripId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "wave", nullable = false)
    private Short wave;

    @Column(name = "rank", nullable = false)
    private Short rank;

    @Column(name = "distance_m")
    private Integer distanceM;

    @Column(name = "eta_s")
    private Integer etaS;

    @CreationTimestamp
    @Column(name = "offered_at", nullable = false, updatable = false)
    private Instant offeredAt;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private OfferStatus status;

    @Column(name = "responded_at")
    private Instant respondedAt;
}
