package com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.notification.domain.model.enums.NotificationChannel;
import com.taxai.api.shared.domain.model.enums.ActorType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "notification_preferences", schema = "notification",
        uniqueConstraints = {
                @UniqueConstraint(name = "notification_preferences_owner_type_owner_id_channel_key", columnNames = {"owner_type", "owner_id", "channel"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NotificationPreferenceEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "owner_type", nullable = false)
    private ActorType ownerType;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "channel", nullable = false)
    private NotificationChannel channel;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Column(name = "quiet_from")
    private LocalTime quietFrom;

    @Column(name = "quiet_to")
    private LocalTime quietTo;

    @Column(name = "timezone", nullable = false)
    private String timezone;
}
