package com.taxai.api.booking.domain.model;

import com.taxai.api.booking.domain.model.enums.PetType;
import com.taxai.api.shared.domain.model.BaseEntity;

import java.util.UUID;

public class BookingPet extends BaseEntity<UUID> {

    private final UUID bookingId;
    private final PetType type;
    private final Short quantity;
    private final Boolean inCarrier;
    private final String notes;

    /// --- Constructors ---
    private BookingPet(
            UUID id,
            UUID bookingId,
            PetType type,
            Short quantity,
            Boolean inCarrier,
            String notes
    ) {
        this.id = id;
        this.bookingId = bookingId;
        this.type = type;
        this.quantity = quantity;
        this.inCarrier = inCarrier;
        this.notes = notes;

       // this.validateData();
    }

    public static BookingPet create(
            UUID id,
            UUID bookingId,
            PetType type,
            Short quantity,
            Boolean inCarrier,
            String notes
    ) {
        return new BookingPet(
                id,
                bookingId,
                type,
                quantity,
                inCarrier,
                notes
        );
    }

    public static BookingPet reconstruct(
            UUID id,
            UUID bookingId,
            PetType type,
            Short quantity,
            Boolean inCarrier,
            String notes
    ) {
        return new BookingPet(
                id,
                bookingId,
                type,
                quantity,
                inCarrier,
                notes
        );
    }

    /// --- Getters ---
    public UUID getBookingId() {
        return bookingId;
    }

    public PetType getType() {
        return type;
    }

    public Short getQuantity() {
        return quantity;
    }

    public Boolean getInCarrier() {
        return inCarrier;
    }

    public String getNotes() {
        return notes;
    }

    /// --- Business Logic ---
}
