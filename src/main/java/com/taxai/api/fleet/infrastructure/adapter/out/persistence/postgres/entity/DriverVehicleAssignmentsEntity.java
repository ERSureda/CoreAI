package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "driver_vehicle_assignments", schema = "fleet")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DriverVehicleAssignmentsEntity {

    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @CreationTimestamp
    @Column(name = "valid_from", nullable = false, updatable = false)
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "created_by")
    private UUID createdBy;
}
