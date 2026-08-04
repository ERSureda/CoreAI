package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "credentials", schema = "iam")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CredentialsEntity {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "failed_login_count", nullable = false)
    private Short failedLoginCount;

    @Column(name = "locked_until")
    private Instant lockedUntil;

    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
