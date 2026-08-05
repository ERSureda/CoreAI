package com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.notification.domain.model.NotificationPreference;
import com.taxai.api.notification.infrastructure.adapter.out.persistence.postgres.entity.NotificationPreferenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface NotificationPreferenceMapper {

    default NotificationPreferenceEntity toEntity(NotificationPreference domain) {
        if (domain == null) {
            return null;
        }
        NotificationPreferenceEntity entity = new NotificationPreferenceEntity();
        entity.setId(domain.getId());
        entity.setOwnerType(domain.getOwnerType());
        entity.setOwnerId(domain.getOwnerId());
        entity.setChannel(domain.getChannel());
        entity.setEnabled(domain.getEnabled());
        entity.setQuietFrom(domain.getQuietFrom());
        entity.setQuietTo(domain.getQuietTo());
        entity.setTimezone(domain.getTimezone());
        return entity;
    }

    default NotificationPreference toDomain(NotificationPreferenceEntity entity) {
        if (entity == null) {
            return null;
        }
        return NotificationPreference.reconstruct(
                entity.getId(),
                entity.getOwnerType(),
                entity.getOwnerId(),
                entity.getChannel(),
                entity.getEnabled(),
                entity.getQuietFrom(),
                entity.getQuietTo(),
                entity.getTimezone()
        );
    }
}
