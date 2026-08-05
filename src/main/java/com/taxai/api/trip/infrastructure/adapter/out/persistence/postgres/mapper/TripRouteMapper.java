package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.trip.domain.model.TripRoute;
import com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity.TripRouteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TripRouteMapper {

    default TripRouteEntity toEntity(TripRoute domain) {
        if (domain == null) {
            return null;
        }
        TripRouteEntity entity = new TripRouteEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setRouteVersion(domain.getRouteVersion());
        entity.setReason(domain.getReason());
        entity.setProvider(domain.getProvider());
        entity.setEncodedPolyline(domain.getEncodedPolyline());
        entity.setDistanceM(domain.getDistanceM());
        entity.setDurationS(domain.getDurationS());
        entity.setDurationTrafficS(domain.getDurationTrafficS());
        entity.setWaypoints(domain.getWaypoints());
        entity.setComputedAt(domain.getComputedAt());
        return entity;
    }

    default TripRoute toDomain(TripRouteEntity entity) {
        if (entity == null) {
            return null;
        }
        return TripRoute.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getRouteVersion(),
                entity.getReason(),
                entity.getProvider(),
                entity.getEncodedPolyline(),
                entity.getDistanceM(),
                entity.getDurationS(),
                entity.getDurationTrafficS(),
                entity.getWaypoints(),
                entity.getComputedAt()
        );
    }
}
