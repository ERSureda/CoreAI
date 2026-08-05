package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.fleet.domain.model.Tenant;
import com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity.TenantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TenantMapper {

    default TenantEntity toEntity(Tenant domain) {
        if (domain == null) {
            return null;
        }
        TenantEntity entity = new TenantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setTaxId(domain.getTaxId());
        entity.setIsActive(domain.getIsActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Tenant toDomain(TenantEntity entity) {
        if (entity == null) {
            return null;
        }
        return Tenant.reconstruct(
                entity.getId(),
                entity.getName(),
                entity.getTaxId(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
