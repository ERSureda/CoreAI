package com.taxai.api.iam.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken extends AggregateRoot<UUID> {

    private final UUID userId;
    private final String tokenHash;
    private final Instant expiresAt;
    private Instant revokedAt;
    private final Instant createdAt;
    private final String createdIp;
    private final String userAgent;

    /// --- Constructors ---
    private RefreshToken(
            UUID id,
            UUID userId,
            String tokenHash,
            Instant expiresAt,
            Instant revokedAt,
            Instant createdAt,
            String createdIp,
            String userAgent
    ) {
        this.id = id;
        this.userId = userId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.revokedAt = revokedAt;
        this.createdAt = createdAt;
        this.createdIp = createdIp;
        this.userAgent = userAgent;

       // this.validateData();
    }

    public static RefreshToken create(
            UUID id,
            UUID userId,
            String tokenHash,
            Instant expiresAt,
            String createdIp,
            String userAgent
    ) {
        return new RefreshToken(
                id,
                userId,
                tokenHash,
                expiresAt,
                null,
                Instant.now(),
                createdIp,
                userAgent
        );
    }

    public static RefreshToken reconstruct(
            UUID id,
            UUID userId,
            String tokenHash,
            Instant expiresAt,
            Instant revokedAt,
            Instant createdAt,
            String createdIp,
            String userAgent
    ) {
        return new RefreshToken(
                id,
                userId,
                tokenHash,
                expiresAt,
                revokedAt,
                createdAt,
                createdIp,
                userAgent
        );
    }

    /// --- Getters ---
    public UUID getUserId() {
        return userId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public String getUserAgent() {
        return userAgent;
    }

    /// --- Business Logic ---
}
