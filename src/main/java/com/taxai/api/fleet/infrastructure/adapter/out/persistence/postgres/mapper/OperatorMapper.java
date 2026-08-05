package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.fleet.domain.model.Operator;
import com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity.OperatorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OperatorMapper {

    default OperatorEntity toEntity(Operator domain) {
        if (domain == null) {
            return null;
        }
        OperatorEntity entity = new OperatorEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setUserId(domain.getUserId());
        entity.setFullName(domain.getFullName());
        entity.setRole(domain.getRole());
        entity.setIsActive(domain.getIsActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Operator toDomain(OperatorEntity entity) {
        if (entity == null) {
            return null;
        }
        return Operator.reconstruct(
                entity.getId(),
                entity.getTenantId(),
                entity.getUserId(),
                entity.getFullName(),
                entity.getRole(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
