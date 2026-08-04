package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.iam.domain.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "iam",
        uniqueConstraints = {
                @UniqueConstraint(name = "users_email_key", columnNames = {"email"}),
                @UniqueConstraint(name = "users_phone_key", columnNames = {"phone"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsersEntity {

    @Id
    private UUID id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
