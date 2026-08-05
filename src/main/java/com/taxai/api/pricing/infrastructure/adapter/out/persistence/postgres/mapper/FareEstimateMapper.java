package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.pricing.domain.model.FareEstimate;
import com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity.FareEstimateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FareEstimateMapper {

    default FareEstimateEntity toEntity(FareEstimate domain) {
        if (domain == null) {
            return null;
        }
        FareEstimateEntity entity = new FareEstimateEntity();
        entity.setId(domain.getId());
        entity.setBookingId(domain.getBookingId());
        entity.setTariffId(domain.getTariffId());
        entity.setEstimatedMin(domain.getEstimatedMin());
        entity.setEstimatedMax(domain.getEstimatedMax());
        entity.setCurrency(domain.getCurrency());
        entity.setCalculatedAt(domain.getCalculatedAt());
        return entity;
    }

    default FareEstimate toDomain(FareEstimateEntity entity) {
        if (entity == null) {
            return null;
        }
        return FareEstimate.reconstruct(
                entity.getId(),
                entity.getBookingId(),
                entity.getTariffId(),
                entity.getEstimatedMin(),
                entity.getEstimatedMax(),
                entity.getCurrency(),
                entity.getCalculatedAt()
        );
    }
}
