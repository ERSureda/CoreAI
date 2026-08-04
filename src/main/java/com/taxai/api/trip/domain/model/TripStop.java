package com.taxai.api.trip.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.trip.domain.model.enums.StopStatus;
import com.taxai.api.trip.domain.model.enums.StopType;

import java.time.Instant;
import java.util.UUID;

public class TripStop extends AggregateRoot<UUID> {

    private final UUID tripId;
    private final Short seq;
    private final StopType type;
    private StopStatus status;
    private final UUID locationId;
    private final String addressSnapshot;
    private final String contactName;
    private final String contactPhone;
    private Instant etaAt;
    private Instant arrivedAt;
    private Instant departedAt;
    private final String notes;

    /// --- Constructors ---
    private TripStop(
            UUID id,
            UUID tripId,
            Short seq,
            StopType type,
            StopStatus status,
            UUID locationId,
            String addressSnapshot,
            String contactName,
            String contactPhone,
            Instant etaAt,
            Instant arrivedAt,
            Instant departedAt,
            String notes
    ) {
        this.id = id;
        this.tripId = tripId;
        this.seq = seq;
        this.type = type;
        this.status = status;
        this.locationId = locationId;
        this.addressSnapshot = addressSnapshot;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.etaAt = etaAt;
        this.arrivedAt = arrivedAt;
        this.departedAt = departedAt;
        this.notes = notes;

       // this.validateData();
    }

    public static TripStop create(
            UUID id,
            UUID tripId,
            Short seq,
            StopType type,
            UUID locationId,
            String addressSnapshot,
            String contactName,
            String contactPhone,
            String notes
    ) {
        return new TripStop(
                id,
                tripId,
                seq,
                type,
                StopStatus.PENDING,
                locationId,
                addressSnapshot,
                contactName,
                contactPhone,
                null,
                null,
                null,
                notes
        );
    }

    public static TripStop reconstruct(
            UUID id,
            UUID tripId,
            Short seq,
            StopType type,
            StopStatus status,
            UUID locationId,
            String addressSnapshot,
            String contactName,
            String contactPhone,
            Instant etaAt,
            Instant arrivedAt,
            Instant departedAt,
            String notes
    ) {
        return new TripStop(
                id,
                tripId,
                seq,
                type,
                status,
                locationId,
                addressSnapshot,
                contactName,
                contactPhone,
                etaAt,
                arrivedAt,
                departedAt,
                notes
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public Short getSeq() {
        return seq;
    }

    public StopType getType() {
        return type;
    }

    public StopStatus getStatus() {
        return status;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public String getAddressSnapshot() {
        return addressSnapshot;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Instant getEtaAt() {
        return etaAt;
    }

    public Instant getArrivedAt() {
        return arrivedAt;
    }

    public Instant getDepartedAt() {
        return departedAt;
    }

    public String getNotes() {
        return notes;
    }

    /// --- Business Logic ---
}
