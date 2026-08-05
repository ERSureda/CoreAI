package com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.notification.domain.model.NotificationTemplate;
import com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.entity.NotificationTemplateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface NotificationTemplateMapper {

    default NotificationTemplateEntity toEntity(NotificationTemplate domain) {
        if (domain == null) {
            return null;
        }
        NotificationTemplateEntity entity = new NotificationTemplateEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setChannel(domain.getChannel());
        entity.setLanguage(domain.getLanguage());
        entity.setSubject(domain.getSubject());
        entity.setBody(domain.getBody());
        entity.setVersion(domain.getVersion());
        entity.setActive(domain.getActive());
        return entity;
    }

    default NotificationTemplate toDomain(NotificationTemplateEntity entity) {
        if (entity == null) {
            return null;
        }
        return NotificationTemplate.reconstruct(
                entity.getId(),
                entity.getCode(),
                entity.getChannel(),
                entity.getLanguage(),
                entity.getSubject(),
                entity.getBody(),
                entity.getVersion(),
                entity.getActive()
        );
    }
}
