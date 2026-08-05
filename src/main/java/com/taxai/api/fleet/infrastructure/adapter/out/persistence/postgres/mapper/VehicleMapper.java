package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.fleet.domain.model.Vehicle;
import com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity.VehicleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface VehicleMapper {

    default VehicleEntity toEntity(Vehicle domain) {
        if (domain == null) {
            return null;
        }
        VehicleEntity entity = new VehicleEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setPlate(domain.getPlate());
        entity.setMake(domain.getMake());
        entity.setModel(domain.getModel());
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setPassengerSeats(domain.getPassengerSeats());
        entity.setWheelchairAccessible(domain.getWheelchairAccessible());
        entity.setAllowsPets(domain.getAllowsPets());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Vehicle toDomain(VehicleEntity entity) {
        if (entity == null) {
            return null;
        }
        return Vehicle.reconstruct(
                entity.getId(),
                entity.getTenantId(),
                entity.getPlate(),
                entity.getMake(),
                entity.getModel(),
                entity.getType(),
                entity.getStatus(),
                entity.getPassengerSeats(),
                entity.getWheelchairAccessible(),
                entity.getAllowsPets(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
