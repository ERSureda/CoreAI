package com.taxai.api.support.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.support.domain.model.enums.LostItemStatus;

import java.time.Instant;
import java.util.UUID;

public class LostItem extends AggregateRoot<UUID> {

    private final UUID tripId;
    private final UUID incidentId;
    private final String description;
    private LostItemStatus status;
    private String storageLocation;
    private String contactPhone;
    private Instant foundAt;
    private Instant returnedAt;
    private final Instant createdAt;

    /// --- Constructors ---
    private LostItem(
            UUID id,
            UUID tripId,
            UUID incidentId,
            String description,
            LostItemStatus status,
            String storageLocation,
            String contactPhone,
            Instant foundAt,
            Instant returnedAt,
            Instant createdAt
    ) {
        this.id = id;
        this.tripId = tripId;
        this.incidentId = incidentId;
        this.description = description;
        this.status = status;
        this.storageLocation = storageLocation;
        this.contactPhone = contactPhone;
        this.foundAt = foundAt;
        this.returnedAt = returnedAt;
        this.createdAt = createdAt;

       // this.validateData();
    }

    public static LostItem create(
            UUID id,
            UUID tripId,
            UUID incidentId,
            String description,
            String storageLocation,
            String contactPhone
    ) {
        return new LostItem(
                id,
                tripId,
                incidentId,
                description,
                LostItemStatus.REPORTED,
                storageLocation,
                contactPhone,
                null,
                null,
                Instant.now()
        );
    }

    public static LostItem reconstruct(
            UUID id,
            UUID tripId,
            UUID incidentId,
            String description,
            LostItemStatus status,
            String storageLocation,
            String contactPhone,
            Instant foundAt,
            Instant returnedAt,
            Instant createdAt
    ) {
        return new LostItem(
                id,
                tripId,
                incidentId,
                description,
                status,
                storageLocation,
                contactPhone,
                foundAt,
                returnedAt,
                createdAt
        );
    }

    /// --- Getters ---
    public UUID getTripId() {
        return tripId;
    }

    public UUID getIncidentId() {
        return incidentId;
    }

    public String getDescription() {
        return description;
    }

    public LostItemStatus getStatus() {
        return status;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Instant getFoundAt() {
        return foundAt;
    }

    public Instant getReturnedAt() {
        return returnedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    /// --- Business Logic ---
}
