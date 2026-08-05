package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.trip.domain.model.TripStop;
import com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity.TripStopEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TripStopMapper {

    default TripStopEntity toEntity(TripStop domain) {
        if (domain == null) {
            return null;
        }
        TripStopEntity entity = new TripStopEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setSeq(domain.getSeq());
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setLocationId(domain.getLocationId());
        entity.setAddressSnapshot(domain.getAddressSnapshot());
        entity.setContactName(domain.getContactName());
        entity.setContactPhone(domain.getContactPhone());
        entity.setEtaAt(domain.getEtaAt());
        entity.setArrivedAt(domain.getArrivedAt());
        entity.setDepartedAt(domain.getDepartedAt());
        entity.setNotes(domain.getNotes());
        return entity;
    }

    default TripStop toDomain(TripStopEntity entity) {
        if (entity == null) {
            return null;
        }
        return TripStop.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getSeq(),
                entity.getType(),
                entity.getStatus(),
                entity.getLocationId(),
                entity.getAddressSnapshot(),
                entity.getContactName(),
                entity.getContactPhone(),
                entity.getEtaAt(),
                entity.getArrivedAt(),
                entity.getDepartedAt(),
                entity.getNotes()
        );
    }
}
