package com.taxai.api.fleet.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class DriverVehicleAssignment extends AggregateRoot<UUID> {

    private final UUID tenantId;
    private final UUID driverId;
    private final UUID vehicleId;
    private final Instant validFrom;
    private Instant validTo;
    private final UUID createdBy;

    /// --- Constructors ---
    private DriverVehicleAssignment(
            UUID id,
            UUID tenantId,
            UUID driverId,
            UUID vehicleId,
            Instant validFrom,
            Instant validTo,
            UUID createdBy
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdBy = createdBy;

       // this.validateData();
    }

    public static DriverVehicleAssignment create(
            UUID id,
            UUID tenantId,
            UUID driverId,
            UUID vehicleId,
            UUID createdBy
    ) {
        return new DriverVehicleAssignment(
                id,
                tenantId,
                driverId,
                vehicleId,
                Instant.now(),
                null,
                createdBy
        );
    }

    public static DriverVehicleAssignment reconstruct(
            UUID id,
            UUID tenantId,
            UUID driverId,
            UUID vehicleId,
            Instant validFrom,
            Instant validTo,
            UUID createdBy
    ) {
        return new DriverVehicleAssignment(
                id,
                tenantId,
                driverId,
                vehicleId,
                validFrom,
                validTo,
                createdBy
        );
    }

    /// --- Getters ---
    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getDriverId() {
        return driverId;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    /// --- Business Logic ---
}
