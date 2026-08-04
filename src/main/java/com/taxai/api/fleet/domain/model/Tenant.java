package com.taxai.api.fleet.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class Tenant extends AggregateRoot<UUID> {

    private final String name;
    private final String taxId;
    private Boolean isActive;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Tenant(
            UUID id,
            String name,
            String taxId,
            Boolean isActive,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.taxId = taxId;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Tenant create(
            UUID id,
            String name,
            String taxId
    ) {
        Instant now = Instant.now();
        return new Tenant(
                id,
                name,
                taxId,
                true,
                now,
                now
        );
    }

    public static Tenant reconstruct(
            UUID id,
            String name,
            String taxId,
            Boolean isActive,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Tenant(
                id,
                name,
                taxId,
                isActive,
                createdAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public String getName() {
        return name;
    }

    public String getTaxId() {
        return taxId;
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
