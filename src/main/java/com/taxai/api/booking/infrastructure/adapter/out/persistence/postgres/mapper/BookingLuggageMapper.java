package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.booking.domain.model.BookingLuggage;
import com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity.BookingLuggageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BookingLuggageMapper {

    default BookingLuggageEntity toEntity(BookingLuggage domain) {
        if (domain == null) {
            return null;
        }
        BookingLuggageEntity entity = new BookingLuggageEntity();
        entity.setId(domain.getId());
        entity.setBookingId(domain.getBookingId());
        entity.setType(domain.getType());
        entity.setQuantity(domain.getQuantity());
        entity.setNotes(domain.getNotes());
        return entity;
    }

    default BookingLuggage toDomain(BookingLuggageEntity entity) {
        if (entity == null) {
            return null;
        }
        return BookingLuggage.reconstruct(
                entity.getId(),
                entity.getBookingId(),
                entity.getType(),
                entity.getQuantity(),
                entity.getNotes()
        );
    }
}
