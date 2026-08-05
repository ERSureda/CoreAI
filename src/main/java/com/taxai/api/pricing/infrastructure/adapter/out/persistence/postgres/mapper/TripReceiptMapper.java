package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.pricing.domain.model.TripReceipt;
import com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity.TripReceiptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TripReceiptMapper {

    default TripReceiptEntity toEntity(TripReceipt domain) {
        if (domain == null) {
            return null;
        }
        TripReceiptEntity entity = new TripReceiptEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setPaymentMethod(domain.getPaymentMethod());
        entity.setTotalAmount(domain.getTotalAmount());
        entity.setCurrency(domain.getCurrency());
        entity.setDriverNotes(domain.getDriverNotes());
        entity.setRecordedAt(domain.getRecordedAt());
        return entity;
    }

    default TripReceipt toDomain(TripReceiptEntity entity) {
        if (entity == null) {
            return null;
        }
        return TripReceipt.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getPaymentMethod(),
                entity.getTotalAmount(),
                entity.getCurrency(),
                entity.getDriverNotes(),
                entity.getRecordedAt()
        );
    }
}
