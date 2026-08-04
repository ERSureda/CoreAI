package com.taxai.api.fleet.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class Operator extends AggregateRoot<UUID> {

    private final UUID tenantId;
    private final UUID userId;
    private final String fullName;
    private final String role;
    private Boolean isActive;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Operator(
            UUID id,
            UUID tenantId,
            UUID userId,
            String fullName,
            String role,
            Boolean isActive,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.userId = userId;
        this.fullName = fullName;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Operator create(
            UUID id,
            UUID tenantId,
            UUID userId,
            String fullName,
            String role
    ) {
        Instant now = Instant.now();
        return new Operator(
                id,
                tenantId,
                userId,
                fullName,
                role,
                true,
                now,
                now
        );
    }

    public static Operator reconstruct(
            UUID id,
            UUID tenantId,
            UUID userId,
            String fullName,
            String role,
            Boolean isActive,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Operator(
                id,
                tenantId,
                userId,
                fullName,
                role,
                isActive,
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

    public String getFullName() {
        return fullName;
    }

    public String getRole() {
        return role;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
