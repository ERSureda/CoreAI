package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.trip.domain.model.Trip;
import com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity.TripEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TripMapper {

    default TripEntity toEntity(Trip domain) {
        if (domain == null) {
            return null;
        }
        TripEntity entity = new TripEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setBookingId(domain.getBookingId());
        entity.setPassengerId(domain.getPassengerId());
        entity.setDriverId(domain.getDriverId());
        entity.setVehicleId(domain.getVehicleId());
        entity.setStatus(domain.getStatus());
        entity.setReplacesTripId(domain.getReplacesTripId());
        entity.setScheduledPickupAt(domain.getScheduledPickupAt());
        entity.setSearchStartedAt(domain.getSearchStartedAt());
        entity.setSearchExpiresAt(domain.getSearchExpiresAt());
        entity.setAssignedAt(domain.getAssignedAt());
        entity.setAcceptedAt(domain.getAcceptedAt());
        entity.setArrivingStartedAt(domain.getArrivingStartedAt());
        entity.setWaitingStartedAt(domain.getWaitingStartedAt());
        entity.setWaitDeadlineAt(domain.getWaitDeadlineAt());
        entity.setBoardedAt(domain.getBoardedAt());
        entity.setCompletedAt(domain.getCompletedAt());
        entity.setCancelledAt(domain.getCancelledAt());
        entity.setCancelledBy(domain.getCancelledBy());
        entity.setCancelReason(domain.getCancelReason());
        entity.setCancelNote(domain.getCancelNote());
        entity.setEstimatedDistanceM(domain.getEstimatedDistanceM());
        entity.setEstimatedDurationS(domain.getEstimatedDurationS());
        entity.setActualDistanceM(domain.getActualDistanceM());
        entity.setActualDurationS(domain.getActualDurationS());
        entity.setActiveRouteVersion(domain.getActiveRouteVersion());
        entity.setVersion(domain.getVersion());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Trip toDomain(TripEntity entity) {
        if (entity == null) {
            return null;
        }
        return Trip.reconstruct(
                entity.getId(),
                entity.getTenantId(),
                entity.getBookingId(),
                entity.getPassengerId(),
                entity.getDriverId(),
                entity.getVehicleId(),
                entity.getStatus(),
                entity.getReplacesTripId(),
                entity.getScheduledPickupAt(),
                entity.getSearchStartedAt(),
                entity.getSearchExpiresAt(),
                entity.getAssignedAt(),
                entity.getAcceptedAt(),
                entity.getArrivingStartedAt(),
                entity.getWaitingStartedAt(),
                entity.getWaitDeadlineAt(),
                entity.getBoardedAt(),
                entity.getCompletedAt(),
                entity.getCancelledAt(),
                entity.getCancelledBy(),
                entity.getCancelReason(),
                entity.getCancelNote(),
                entity.getEstimatedDistanceM(),
                entity.getEstimatedDurationS(),
                entity.getActualDistanceM(),
                entity.getActualDurationS(),
                entity.getActiveRouteVersion(),
                entity.getVersion(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
