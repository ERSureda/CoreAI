package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.trip.domain.model.enums.RouteReason;
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
@Table(name = "trip_routes", schema = "trip",
        uniqueConstraints = {
                @UniqueConstraint(name = "trip_routes_trip_id_route_version_key", columnNames = {"trip_id", "route_version"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TripRoutesEntity {

    @Id
    private UUID id;

    @Column(name = "trip_id", nullable = false)
    private UUID tripId;

    @Column(name = "route_version", nullable = false)
    private Short routeVersion;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "reason", nullable = false)
    private RouteReason reason;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "encoded_polyline", nullable = false)
    private String encodedPolyline;

    @Column(name = "distance_m", nullable = false)
    private Integer distanceM;

    @Column(name = "duration_s", nullable = false)
    private Integer durationS;

    @Column(name = "duration_traffic_s")
    private Integer durationTrafficS;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "waypoints")
    private String waypoints;

    @CreationTimestamp
    @Column(name = "computed_at", nullable = false, updatable = false)
    private Instant computedAt;
}
