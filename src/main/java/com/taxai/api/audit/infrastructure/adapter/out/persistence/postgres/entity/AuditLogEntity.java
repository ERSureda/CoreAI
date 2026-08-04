package com.taxai.api.audit.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.shared.domain.model.enums.ActorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "audit_log", schema = "audit",
        indexes = {
                @Index(name = "idx_audit_actor", columnList = "actor_type, actor_id, occurred_at"),
                @Index(name = "idx_audit_aggregate", columnList = "aggregate_type, aggregate_id, occurred_at")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class AuditLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "occurred_at", nullable = false, updatable = false)
    private Instant occurredAt;

    @Column(name = "aggregate_type", nullable = false)
    private String aggregateType;

    @Column(name = "aggregate_id", nullable = false)
    private UUID aggregateId;

    @Column(name = "action", nullable = false)
    private String action;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "actor_type", nullable = false)
    private ActorType actorType;

    @Column(name = "actor_id")
    private UUID actorId;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "event_id")
    private UUID eventId;
}
