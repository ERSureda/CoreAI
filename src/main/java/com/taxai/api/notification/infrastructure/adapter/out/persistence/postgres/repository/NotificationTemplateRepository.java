package com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.entity.NotificationTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplateEntity, UUID> {
}
