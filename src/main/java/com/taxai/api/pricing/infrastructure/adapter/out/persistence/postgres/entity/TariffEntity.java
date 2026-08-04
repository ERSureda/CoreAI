package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.shared.domain.model.enums.VehicleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tariffs", schema = "pricing")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TariffEntity {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "zone_id")
    private UUID zoneId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "base_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal baseFare;

    @Column(name = "per_km", nullable = false, precision = 10, scale = 4)
    private BigDecimal perKm;

    @Column(name = "per_min", nullable = false, precision = 10, scale = 4)
    private BigDecimal perMin;

    @Column(name = "min_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal minFare;

    @Column(name = "waiting_per_min", nullable = false, precision = 10, scale = 4)
    private BigDecimal waitingPerMin;

    @Column(name = "night_surcharge_pct", nullable = false, precision = 5, scale = 2)
    private BigDecimal nightSurchargePct;

    @Column(name = "airport_surcharge", nullable = false, precision = 10, scale = 2)
    private BigDecimal airportSurcharge;

    @Column(name = "valid_from", nullable = false)
    private LocalDate validFrom;

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
