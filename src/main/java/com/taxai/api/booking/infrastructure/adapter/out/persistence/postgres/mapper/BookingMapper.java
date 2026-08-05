package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.booking.domain.model.Booking;
import com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BookingMapper {

    default BookingEntity toEntity(Booking domain) {
        if (domain == null) {
            return null;
        }
        BookingEntity entity = new BookingEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setLocator(domain.getLocator());
        entity.setPassengerId(domain.getPassengerId());
        entity.setChannel(domain.getChannel());
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setScheduledPickupAt(domain.getScheduledPickupAt());
        entity.setPickupTimezone(domain.getPickupTimezone());
        entity.setRecurrenceRule(domain.getRecurrenceRule());
        entity.setRecurrenceUntil(domain.getRecurrenceUntil());
        entity.setPassengerCount(domain.getPassengerCount());
        entity.setVehicleTypeRequired(domain.getVehicleTypeRequired());
        entity.setNeedsChildSeat(domain.getNeedsChildSeat());
        entity.setWheelchairRequired(domain.getWheelchairRequired());
        entity.setNotes(domain.getNotes());
        entity.setSourceConversationId(domain.getSourceConversationId());
        entity.setCreatedByActor(domain.getCreatedByActor());
        entity.setCreatedById(domain.getCreatedById());
        entity.setVersion(domain.getVersion());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Booking toDomain(BookingEntity entity) {
        if (entity == null) {
            return null;
        }
        return Booking.reconstruct(
                entity.getId(),
                entity.getTenantId(),
                entity.getLocator(),
                entity.getPassengerId(),
                entity.getChannel(),
                entity.getType(),
                entity.getStatus(),
                entity.getScheduledPickupAt(),
                entity.getPickupTimezone(),
                entity.getRecurrenceRule(),
                entity.getRecurrenceUntil(),
                entity.getPassengerCount(),
                entity.getVehicleTypeRequired(),
                entity.getNeedsChildSeat(),
                entity.getWheelchairRequired(),
                entity.getNotes(),
                entity.getSourceConversationId(),
                entity.getCreatedByActor(),
                entity.getCreatedById(),
                entity.getVersion(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
