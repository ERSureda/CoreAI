package com.taxai.api.fleet.domain.model;

import com.taxai.api.fleet.domain.model.enums.VehicleStatus;
import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.VehicleType;

import java.time.Instant;
import java.util.UUID;

public class Vehicle extends AggregateRoot<UUID> {

    private final UUID tenantId;
    private final String plate;
    private final String make;
    private final String model;
    private final VehicleType type;
    private VehicleStatus status;
    private final Short passengerSeats;
    private final Boolean wheelchairAccessible;
    private final Boolean allowsPets;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Vehicle(
            UUID id,
            UUID tenantId,
            String plate,
            String make,
            String model,
            VehicleType type,
            VehicleStatus status,
            Short passengerSeats,
            Boolean wheelchairAccessible,
            Boolean allowsPets,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.plate = plate;
        this.make = make;
        this.model = model;
        this.type = type;
        this.status = status;
        this.passengerSeats = passengerSeats;
        this.wheelchairAccessible = wheelchairAccessible;
        this.allowsPets = allowsPets;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Vehicle create(
            UUID id,
            UUID tenantId,
            String plate,
            String make,
            String model,
            VehicleType type,
            Short passengerSeats,
            Boolean wheelchairAccessible,
            Boolean allowsPets
    ) {
        Instant now = Instant.now();
        return new Vehicle(
                id,
                tenantId,
                plate,
                make,
                model,
                type,
                VehicleStatus.ACTIVE,
                passengerSeats,
                wheelchairAccessible,
                allowsPets,
                now,
                now
        );
    }

    public static Vehicle reconstruct(
            UUID id,
            UUID tenantId,
            String plate,
            String make,
            String model,
            VehicleType type,
            VehicleStatus status,
            Short passengerSeats,
            Boolean wheelchairAccessible,
            Boolean allowsPets,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Vehicle(
                id,
                tenantId,
                plate,
                make,
                model,
                type,
                status,
                passengerSeats,
                wheelchairAccessible,
                allowsPets,
                createdAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public UUID getTenantId() {
        return tenantId;
    }

    public String getPlate() {
        return plate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public VehicleType getType() {
        return type;
    }

    public VehicleStatus getStatus() {
        return status;
    }

    public Short getPassengerSeats() {
        return passengerSeats;
    }

    public Boolean getWheelchairAccessible() {
        return wheelchairAccessible;
    }

    public Boolean getAllowsPets() {
        return allowsPets;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
