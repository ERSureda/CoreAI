package com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.support.domain.model.LostItem;
import com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.entity.LostItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LostItemMapper {

    default LostItemEntity toEntity(LostItem domain) {
        if (domain == null) {
            return null;
        }
        LostItemEntity entity = new LostItemEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setIncidentId(domain.getIncidentId());
        entity.setDescription(domain.getDescription());
        entity.setStatus(domain.getStatus());
        entity.setStorageLocation(domain.getStorageLocation());
        entity.setContactPhone(domain.getContactPhone());
        entity.setFoundAt(domain.getFoundAt());
        entity.setReturnedAt(domain.getReturnedAt());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    default LostItem toDomain(LostItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return LostItem.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getIncidentId(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getStorageLocation(),
                entity.getContactPhone(),
                entity.getFoundAt(),
                entity.getReturnedAt(),
                entity.getCreatedAt()
        );
    }
}
