package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.booking.domain.model.BookingPet;
import com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity.BookingPetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface BookingPetMapper {

    default BookingPetEntity toEntity(BookingPet domain) {
        if (domain == null) {
            return null;
        }
        BookingPetEntity entity = new BookingPetEntity();
        entity.setId(domain.getId());
        entity.setBookingId(domain.getBookingId());
        entity.setType(domain.getType());
        entity.setQuantity(domain.getQuantity());
        entity.setInCarrier(domain.getInCarrier());
        entity.setNotes(domain.getNotes());
        return entity;
    }

    default BookingPet toDomain(BookingPetEntity entity) {
        if (entity == null) {
            return null;
        }
        return BookingPet.reconstruct(
                entity.getId(),
                entity.getBookingId(),
                entity.getType(),
                entity.getQuantity(),
                entity.getInCarrier(),
                entity.getNotes()
        );
    }
}
