package com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.notification.domain.model.enums.NotificationChannel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "notification_templates", schema = "notification",
        uniqueConstraints = {
                @UniqueConstraint(name = "notification_templates_code_channel_language_version_key", columnNames = {"code", "channel", "language", "version"})
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NotificationTemplatesEntity {

    @Id
    private UUID id;

    @Column(name = "code", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "channel", nullable = false)
    private NotificationChannel channel;

    @Column(name = "language", nullable = false, length = 2)
    private String language;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "version", nullable = false)
    private Short version;

    @Column(name = "active", nullable = false)
    private Boolean active;
}
