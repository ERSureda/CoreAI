package com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.support.domain.model.enums.LostItemStatus;
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
@Table(name = "lost_items", schema = "support",
        indexes = {
                @Index(name = "idx_lost_items_trip", columnList = "trip_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class LostItemsEntity {

    @Id
    private UUID id;

    @Column(name = "trip_id", nullable = false)
    private UUID tripId;

    @Column(name = "incident_id")
    private UUID incidentId;

    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "status", nullable = false)
    private LostItemStatus status;

    @Column(name = "storage_location")
    private String storageLocation;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "found_at")
    private Instant foundAt;

    @Column(name = "returned_at")
    private Instant returnedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
