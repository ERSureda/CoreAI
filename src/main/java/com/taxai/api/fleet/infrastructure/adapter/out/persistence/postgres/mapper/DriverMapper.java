package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.fleet.domain.model.Driver;
import com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity.DriverEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DriverMapper {

    default DriverEntity toEntity(Driver domain) {
        if (domain == null) {
            return null;
        }
        DriverEntity entity = new DriverEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setUserId(domain.getUserId());
        entity.setEmployeeCode(domain.getEmployeeCode());
        entity.setFullName(domain.getFullName());
        entity.setPhone(domain.getPhone());
        entity.setAdminStatus(domain.getAdminStatus());
        entity.setDefaultVehicleId(domain.getDefaultVehicleId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Driver toDomain(DriverEntity entity) {
        if (entity == null) {
            return null;
        }
        return Driver.reconstruct(
                entity.getId(),
                entity.getTenantId(),
                entity.getUserId(),
                entity.getEmployeeCode(),
                entity.getFullName(),
                entity.getPhone(),
                entity.getAdminStatus(),
                entity.getDefaultVehicleId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
