package com.taxai.api.iam.domain.model;

import com.taxai.api.iam.domain.model.enums.UserStatus;
import com.taxai.api.shared.domain.model.AggregateRoot;

import java.time.Instant;
import java.util.UUID;

public class User extends AggregateRoot<UUID> {

    private final String email;
    private final String phone;
    private UserStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private User(
            UUID id,
            String email,
            String phone,
            UserStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static User create(
            UUID id,
            String email,
            String phone
    ) {
        Instant now = Instant.now();
        return new User(
                id,
                email,
                phone,
                UserStatus.PENDING_VERIFICATION,
                now,
                now
        );
    }

    public static User reconstruct(
            UUID id,
            String email,
            String phone,
            UserStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new User(
                id,
                email,
                phone,
                status,
                createdAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
