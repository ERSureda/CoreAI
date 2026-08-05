package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.trip.domain.model.TripStatusHistory;
import com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity.TripStatusHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TripStatusHistoryMapper {

    default TripStatusHistoryEntity toEntity(TripStatusHistory domain) {
        if (domain == null) {
            return null;
        }
        TripStatusHistoryEntity entity = new TripStatusHistoryEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setFromStatus(domain.getFromStatus());
        entity.setToStatus(domain.getToStatus());
        entity.setActorType(domain.getActorType());
        entity.setActorId(domain.getActorId());
        entity.setReason(domain.getReason());
        entity.setEventId(domain.getEventId());
        entity.setMetadata(domain.getMetadata());
        entity.setOccurredAt(domain.getOccurredAt());
        return entity;
    }

    default TripStatusHistory toDomain(TripStatusHistoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return TripStatusHistory.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getFromStatus(),
                entity.getToStatus(),
                entity.getActorType(),
                entity.getActorId(),
                entity.getReason(),
                entity.getEventId(),
                entity.getMetadata(),
                entity.getOccurredAt()
        );
    }
}
