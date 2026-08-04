package com.taxai.api.booking.domain.model;

import com.taxai.api.booking.domain.model.enums.BookingChannel;
import com.taxai.api.booking.domain.model.enums.BookingStatus;
import com.taxai.api.booking.domain.model.enums.BookingType;
import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.ActorType;
import com.taxai.api.shared.domain.model.enums.VehicleType;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Booking extends AggregateRoot<UUID> {

    private final UUID tenantId;
    private final String locator;
    private final UUID passengerId;
    private final BookingChannel channel;
    private final BookingType type;
    private BookingStatus status;
    private final Instant scheduledPickupAt;
    private final String pickupTimezone;
    private final String recurrenceRule;
    private final LocalDate recurrenceUntil;
    private final Short passengerCount;
    private final VehicleType vehicleTypeRequired;
    private final Boolean needsChildSeat;
    private final Boolean wheelchairRequired;
    private final String notes;
    private final String sourceConversationId;
    private final ActorType createdByActor;
    private final UUID createdById;
    private final Integer version;
    private final Instant createdAt;
    private Instant updatedAt;

    /// --- Constructors ---
    private Booking(
            UUID id,
            UUID tenantId,
            String locator,
            UUID passengerId,
            BookingChannel channel,
            BookingType type,
            BookingStatus status,
            Instant scheduledPickupAt,
            String pickupTimezone,
            String recurrenceRule,
            LocalDate recurrenceUntil,
            Short passengerCount,
            VehicleType vehicleTypeRequired,
            Boolean needsChildSeat,
            Boolean wheelchairRequired,
            String notes,
            String sourceConversationId,
            ActorType createdByActor,
            UUID createdById,
            Integer version,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.locator = locator;
        this.passengerId = passengerId;
        this.channel = channel;
        this.type = type;
        this.status = status;
        this.scheduledPickupAt = scheduledPickupAt;
        this.pickupTimezone = pickupTimezone;
        this.recurrenceRule = recurrenceRule;
        this.recurrenceUntil = recurrenceUntil;
        this.passengerCount = passengerCount;
        this.vehicleTypeRequired = vehicleTypeRequired;
        this.needsChildSeat = needsChildSeat;
        this.wheelchairRequired = wheelchairRequired;
        this.notes = notes;
        this.sourceConversationId = sourceConversationId;
        this.createdByActor = createdByActor;
        this.createdById = createdById;
        this.version = version;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

       // this.validateData();
    }

    public static Booking create(
            UUID id,
            UUID tenantId,
            String locator,
            UUID passengerId,
            BookingChannel channel,
            BookingType type,
            Instant scheduledPickupAt,
            String pickupTimezone,
            String recurrenceRule,
            LocalDate recurrenceUntil,
            Short passengerCount,
            VehicleType vehicleTypeRequired,
            Boolean needsChildSeat,
            Boolean wheelchairRequired,
            String notes,
            String sourceConversationId,
            ActorType createdByActor,
            UUID createdById
    ) {
        Instant now = Instant.now();
        return new Booking(
                id,
                tenantId,
                locator,
                passengerId,
                channel,
                type,
                BookingStatus.CONFIRMED,
                scheduledPickupAt,
                pickupTimezone,
                recurrenceRule,
                recurrenceUntil,
                passengerCount,
                vehicleTypeRequired,
                needsChildSeat,
                wheelchairRequired,
                notes,
                sourceConversationId,
                createdByActor,
                createdById,
                0,
                now,
                now
        );
    }

    public static Booking reconstruct(
            UUID id,
            UUID tenantId,
            String locator,
            UUID passengerId,
            BookingChannel channel,
            BookingType type,
            BookingStatus status,
            Instant scheduledPickupAt,
            String pickupTimezone,
            String recurrenceRule,
            LocalDate recurrenceUntil,
            Short passengerCount,
            VehicleType vehicleTypeRequired,
            Boolean needsChildSeat,
            Boolean wheelchairRequired,
            String notes,
            String sourceConversationId,
            ActorType createdByActor,
            UUID createdById,
            Integer version,
            Instant createdAt,
            Instant updatedAt
    ) {
        return new Booking(
                id,
                tenantId,
                locator,
                passengerId,
                channel,
                type,
                status,
                scheduledPickupAt,
                pickupTimezone,
                recurrenceRule,
                recurrenceUntil,
                passengerCount,
                vehicleTypeRequired,
                needsChildSeat,
                wheelchairRequired,
                notes,
                sourceConversationId,
                createdByActor,
                createdById,
                version,
                createdAt,
                updatedAt
        );
    }

    /// --- Getters ---
    public UUID getTenantId() {
        return tenantId;
    }

    public String getLocator() {
        return locator;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public BookingChannel getChannel() {
        return channel;
    }

    public BookingType getType() {
        return type;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Instant getScheduledPickupAt() {
        return scheduledPickupAt;
    }

    public String getPickupTimezone() {
        return pickupTimezone;
    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public LocalDate getRecurrenceUntil() {
        return recurrenceUntil;
    }

    public Short getPassengerCount() {
        return passengerCount;
    }

    public VehicleType getVehicleTypeRequired() {
        return vehicleTypeRequired;
    }

    public Boolean getNeedsChildSeat() {
        return needsChildSeat;
    }

    public Boolean getWheelchairRequired() {
        return wheelchairRequired;
    }

    public String getNotes() {
        return notes;
    }

    public String getSourceConversationId() {
        return sourceConversationId;
    }

    public ActorType getCreatedByActor() {
        return createdByActor;
    }

    public UUID getCreatedById() {
        return createdById;
    }

    public Integer getVersion() {
        return version;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /// --- Business Logic ---
}
