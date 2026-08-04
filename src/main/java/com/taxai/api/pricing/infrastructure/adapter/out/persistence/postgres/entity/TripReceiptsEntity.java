package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.pricing.domain.model.enums.PaymentIntent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trip_receipts", schema = "pricing",
        uniqueConstraints = {
                @UniqueConstraint(name = "trip_receipts_trip_id_key", columnNames = {"trip_id"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TripReceiptsEntity {

    @Id
    private UUID id;

    @Column(name = "trip_id", nullable = false)
    private UUID tripId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "payment_method", nullable = false)
    private PaymentIntent paymentMethod;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "driver_notes")
    private String driverNotes;

    @CreationTimestamp
    @Column(name = "recorded_at", nullable = false, updatable = false)
    private Instant recordedAt;
}
