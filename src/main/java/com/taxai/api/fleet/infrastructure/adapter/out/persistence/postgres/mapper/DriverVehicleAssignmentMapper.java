package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.fleet.domain.model.DriverVehicleAssignment;
import com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity.DriverVehicleAssignmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DriverVehicleAssignmentMapper {

    default DriverVehicleAssignmentEntity toEntity(DriverVehicleAssignment domain) {
        if (domain == null) {
            return null;
        }
        DriverVehicleAssignmentEntity entity = new DriverVehicleAssignmentEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setDriverId(domain.getDriverId());
        entity.setVehicleId(domain.getVehicleId());
        entity.setValidFrom(domain.getValidFrom());
        entity.setValidTo(domain.getValidTo());
        entity.setCreatedBy(domain.getCreatedBy());
        return entity;
    }

    default DriverVehicleAssignment toDomain(DriverVehicleAssignmentEntity entity) {
        if (entity == null) {
            return null;
        }
        return DriverVehicleAssignment.reconstruct(
                entity.getId(),
                entity.getTenantId(),
                entity.getDriverId(),
                entity.getVehicleId(),
                entity.getValidFrom(),
                entity.getValidTo(),
                entity.getCreatedBy()
        );
    }
}
