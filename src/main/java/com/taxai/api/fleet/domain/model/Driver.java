package com.taxai.api.fleet.domain.model;

import com.taxai.api.fleet.domain.model.enums.DriverAdminStatus;
import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class Driver extends AggregateRoot<UUID> {

    private final UUID tenantId;
    private final UUID userId;
    private final String employeeCode;
    private final String fullName;
    private final String phone;
    private DriverAdminStatus adminStatus;
    private final UUID defaultVehicleId;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Driver(
            UUID id,
            UUID tenantId,
            UUID userId,
            String employeeCode,
            String fullName,
            String phone,
            DriverAdminStatus adminStatus,
            UUID defaultVehicleId,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.userId = userId;
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.phone = phone;
        this.adminStatus = adminStatus;
        this.defaultVehicleId = defaultVehicleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Driver create(
            UUID id,
            UUID tenantId,
            UUID userId,
            String employeeCode,
            String fullName,
            String phone,
            UUID defaultVehicleId
    ) {
        Instant now = Instant.now();
        return new Driver(
                id,
                tenantId,
                userId,
                employeeCode,
                fullName,
                phone,
                DriverAdminStatus.ONBOARDING,
                defaultVehicleId,
                now,
                now
        );
    }

    public static Driver reconstruct(
            UUID id,
            UUID tenantId,
            UUID userId,
            String employeeCode,
            String fullName,
            String phone,
            DriverAdminStatus adminStatus,
            UUID defaultVehicleId,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Driver(
                id,
                tenantId,
                userId,
                employeeCode,
                fullName,
                phone,
                adminStatus,
                defaultVehicleId,
                createdAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public UUID getTenantId() {
        return tenantId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public DriverAdminStatus getAdminStatus() {
        return adminStatus;
    }

    public UUID getDefaultVehicleId() {
        return defaultVehicleId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
