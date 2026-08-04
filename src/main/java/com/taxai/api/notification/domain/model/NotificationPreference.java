package com.taxai.api.notification.domain.model;

import com.taxai.api.notification.domain.model.enums.NotificationChannel;
import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.ActorType;

import java.time.LocalTime;
import java.util.UUID;

public class NotificationPreference extends AggregateRoot<UUID> {

    private final ActorType ownerType;
    private final UUID ownerId;
    private final NotificationChannel channel;
    private Boolean enabled;
    private final LocalTime quietFrom;
    private final LocalTime quietTo;
    private final String timezone;

    /// --- Constructors ---
    private NotificationPreference(
            UUID id,
            ActorType ownerType,
            UUID ownerId,
            NotificationChannel channel,
            Boolean enabled,
            LocalTime quietFrom,
            LocalTime quietTo,
            String timezone
    ) {
        this.id = id;
        this.ownerType = ownerType;
        this.ownerId = ownerId;
        this.channel = channel;
        this.enabled = enabled;
        this.quietFrom = quietFrom;
        this.quietTo = quietTo;
        this.timezone = timezone;

       // this.validateData();
    }

    public static NotificationPreference create(
            UUID id,
            ActorType ownerType,
            UUID ownerId,
            NotificationChannel channel,
            LocalTime quietFrom,
            LocalTime quietTo,
            String timezone
    ) {
        return new NotificationPreference(
                id,
                ownerType,
                ownerId,
                channel,
                true,
                quietFrom,
                quietTo,
                timezone
        );
    }

    public static NotificationPreference reconstruct(
            UUID id,
            ActorType ownerType,
            UUID ownerId,
            NotificationChannel channel,
            Boolean enabled,
            LocalTime quietFrom,
            LocalTime quietTo,
            String timezone
    ) {
        return new NotificationPreference(
                id,
                ownerType,
                ownerId,
                channel,
                enabled,
                quietFrom,
                quietTo,
                timezone
        );
    }

    /// --- Getters ---
    public ActorType getOwnerType() {
        return ownerType;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public LocalTime getQuietFrom() {
        return quietFrom;
    }

    public LocalTime getQuietTo() {
        return quietTo;
    }

    public String getTimezone() {
        return timezone;
    }

    /// --- Business Logic ---
}
