package com.taxai.api.booking.domain.model;

import com.taxai.api.booking.domain.model.enums.LuggageType;
import com.taxai.api.shared.domain.model.BaseEntity;

import java.util.UUID;

public class BookingLuggage extends BaseEntity<UUID> {

    private final UUID bookingId;
    private final LuggageType type;
    private final Short quantity;
    private final String notes;

    /// --- Constructors ---
    private BookingLuggage(
            UUID id,
            UUID bookingId,
            LuggageType type,
            Short quantity,
            String notes
    ) {
        this.id = id;
        this.bookingId = bookingId;
        this.type = type;
        this.quantity = quantity;
        this.notes = notes;

       // this.validateData();
    }

    public static BookingLuggage create(
            UUID id,
            UUID bookingId,
            LuggageType type,
            Short quantity,
            String notes
    ) {
        return new BookingLuggage(
                id,
                bookingId,
                type,
                quantity,
                notes
        );
    }

    public static BookingLuggage reconstruct(
            UUID id,
            UUID bookingId,
            LuggageType type,
            Short quantity,
            String notes
    ) {
        return new BookingLuggage(
                id,
                bookingId,
                type,
                quantity,
                notes
        );
    }

    /// --- Getters ---
    public UUID getBookingId() {
        return bookingId;
    }

    public LuggageType getType() {
        return type;
    }

    public Short getQuantity() {
        return quantity;
    }

    public String getNotes() {
        return notes;
    }

    /// --- Business Logic ---
}
