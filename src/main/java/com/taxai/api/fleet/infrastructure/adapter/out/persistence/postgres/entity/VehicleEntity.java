package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.fleet.domain.model.enums.VehicleStatus;
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
import java.util.UUID;

@Entity
@Table(name = "vehicles", schema = "fleet",
        uniqueConstraints = {
                @UniqueConstraint(name = "vehicles_tenant_id_plate_key", columnNames = {"tenant_id", "plate"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VehicleEntity {

    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "plate", nullable = false)
    private String plate;

    @Column(name = "make", nullable = false)
    private String make;

    @Column(name = "model", nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", nullable = false)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private VehicleStatus status;

    @Column(name = "passenger_seats", nullable = false)
    private Short passengerSeats;

    @Column(name = "wheelchair_accessible", nullable = false)
    private Boolean wheelchairAccessible;

    @Column(name = "allows_pets", nullable = false)
    private Boolean allowsPets;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
