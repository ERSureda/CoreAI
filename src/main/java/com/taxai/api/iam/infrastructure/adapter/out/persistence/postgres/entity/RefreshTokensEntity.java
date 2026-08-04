package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "refresh_tokens", schema = "iam",
        uniqueConstraints = {
                @UniqueConstraint(name = "refresh_tokens_token_hash_key", columnNames = {"token_hash"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RefreshTokensEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "token_hash", nullable = false)
    private String tokenHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "created_ip")
    private String createdIp;

    @Column(name = "user_agent")
    private String userAgent;
}
