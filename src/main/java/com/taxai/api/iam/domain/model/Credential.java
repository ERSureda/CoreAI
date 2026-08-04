package com.taxai.api.iam.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class Credential extends AggregateRoot<UUID> {

    private String passwordHash;
    private Short failedLoginCount;
    private Instant lockedUntil;
    private Instant lastLoginAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Credential(
            UUID id,
            String passwordHash,
            Short failedLoginCount,
            Instant lockedUntil,
            Instant lastLoginAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.passwordHash = passwordHash;
        this.failedLoginCount = failedLoginCount;
        this.lockedUntil = lockedUntil;
        this.lastLoginAt = lastLoginAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Credential create(
            UUID id,
            String passwordHash
    ) {
        return new Credential(
                id,
                passwordHash,
                (short) 0,
                null,
                null,
                Instant.now()
        );
    }

    public static Credential reconstruct(
            UUID id,
            String passwordHash,
            Short failedLoginCount,
            Instant lockedUntil,
            Instant lastLoginAt,
            Instant updatedAt
    ) {
        return new Credential(
                id,
                passwordHash,
                failedLoginCount,
                lockedUntil,
                lastLoginAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public String getPasswordHash() {
        return passwordHash;
    }

    public Short getFailedLoginCount() {
        return failedLoginCount;
    }

    public Instant getLockedUntil() {
        return lockedUntil;
    }

    public Instant getLastLoginAt() {
        return lastLoginAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
