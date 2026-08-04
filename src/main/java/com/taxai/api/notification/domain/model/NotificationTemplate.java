package com.taxai.api.notification.domain.model;

import com.taxai.api.notification.domain.model.enums.NotificationChannel;
import com.taxai.api.shared.domain.model.AggregateRoot;

import java.util.UUID;

public class NotificationTemplate extends AggregateRoot<UUID> {

    private final String code;
    private final NotificationChannel channel;
    private final String language;
    private final String subject;
    private final String body;
    private final Short version;
    private Boolean active;

    /// --- Constructors ---
    private NotificationTemplate(
            UUID id,
            String code,
            NotificationChannel channel,
            String language,
            String subject,
            String body,
            Short version,
            Boolean active
    ) {
        this.id = id;
        this.code = code;
        this.channel = channel;
        this.language = language;
        this.subject = subject;
        this.body = body;
        this.version = version;
        this.active = active;

       // this.validateData();
    }

    public static NotificationTemplate create(
            UUID id,
            String code,
            NotificationChannel channel,
            String language,
            String subject,
            String body,
            Short version
    ) {
        return new NotificationTemplate(
                id,
                code,
                channel,
                language,
                subject,
                body,
                version,
                true
        );
    }

    public static NotificationTemplate reconstruct(
            UUID id,
            String code,
            NotificationChannel channel,
            String language,
            String subject,
            String body,
            Short version,
            Boolean active
    ) {
        return new NotificationTemplate(
                id,
                code,
                channel,
                language,
                subject,
                body,
                version,
                active
        );
    }

    /// --- Getters ---
    public String getCode() {
        return code;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public String getLanguage() {
        return language;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Short getVersion() {
        return version;
    }

    public Boolean getActive() {
        return active;
    }

    /// --- Business Logic ---
}
