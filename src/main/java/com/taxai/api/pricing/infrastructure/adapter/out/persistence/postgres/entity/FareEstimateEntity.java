package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "fare_estimates", schema = "pricing")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FareEstimateEntity {

    @Id
    private UUID id;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Column(name = "tariff_id")
    private UUID tariffId;

    @Column(name = "estimated_min", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedMin;

    @Column(name = "estimated_max", nullable = false, precision = 10, scale = 2)
    private BigDecimal estimatedMax;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @CreationTimestamp
    @Column(name = "calculated_at", nullable = false, updatable = false)
    private Instant calculatedAt;
}
