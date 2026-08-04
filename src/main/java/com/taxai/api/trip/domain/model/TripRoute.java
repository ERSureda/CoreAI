package com.taxai.api.trip.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.trip.domain.model.enums.RouteReason;

import java.time.Instant;
import java.util.UUID;

public class TripRoute extends AggregateRoot<UUID> {

    private final UUID tripId;
    private final Short routeVersion;
    private final RouteReason reason;
    private final String provider;
    private final String encodedPolyline;
    private final Integer distanceM;
    private final Integer durationS;
    private final Integer durationTrafficS;
    private final String waypoints;
    private final Instant computedAt;

    /// --- Constructors ---
    private TripRoute(
            UUID id,
            UUID tripId,
            Short routeVersion,
            RouteReason reason,
            String provider,
            String encodedPolyline,
            Integer distanceM,
            Integer durationS,
            Integer durationTrafficS,
            String waypoints,
            Instant computedAt
    ) {
        this.id = id;
        this.tripId = tripId;
        this.routeVersion = routeVersion;
        this.reason = reason;
        this.provider = provider;
        this.encodedPolyline = encodedPolyline;
        this.distanceM = distanceM;
        this.durationS = durationS;
        this.durationTrafficS = durationTrafficS;
        this.waypoints = waypoints;
        this.computedAt = computedAt;

       // this.validateData();
    }

    public static TripRoute create(
            UUID id,
            UUID tripId,
            Short routeVersion,
            RouteReason reason,
            String provider,
            String encodedPolyline,
            Integer distanceM,
            Integer durationS,
            Integer durationTrafficS,
            String waypoints
    ) {
        return new TripRoute(
                id,
                tripId,
                routeVersion,
                reason,
                provider,
                encodedPolyline,
                distanceM,
                durationS,
                durationTrafficS,
                waypoints,
                Instant.now()
        );
    }

    public static TripRoute reconstruct(
            UUID id,
            UUID tripId,
            Short routeVersion,
            RouteReason reason,
            String provider,
            String encodedPolyline,
            Integer distanceM,
            Integer durationS,
            Integer durationTrafficS,
            String waypoints,
            Instant computedAt
    ) {
        return new TripRoute(
                id,
                tripId,
                routeVersion,
                reason,
                provider,
                encodedPolyline,
                distanceM,
                durationS,
                durationTrafficS,
                waypoints,
                computedAt
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public Short getRouteVersion() {
        return routeVersion;
    }

    public RouteReason getReason() {
        return reason;
    }

    public String getProvider() {
        return provider;
    }

    public String getEncodedPolyline() {
        return encodedPolyline;
    }

    public Integer getDistanceM() {
        return distanceM;
    }

    public Integer getDurationS() {
        return durationS;
    }

    public Integer getDurationTrafficS() {
        return durationTrafficS;
    }

    public String getWaypoints() {
        return waypoints;
    }

    public Instant getComputedAt() {
        return computedAt;
    }

    /// --- Business Logic ---
}
