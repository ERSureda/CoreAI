package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.pricing.domain.model.Tariff;
import com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity.TariffEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TariffMapper {

    default TariffEntity toEntity(Tariff domain) {
        if (domain == null) {
            return null;
        }
        TariffEntity entity = new TariffEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setZoneId(domain.getZoneId());
        entity.setVehicleType(domain.getVehicleType());
        entity.setBaseFare(domain.getBaseFare());
        entity.setPerKm(domain.getPerKm());
        entity.setPerMin(domain.getPerMin());
        entity.setMinFare(domain.getMinFare());
        entity.setWaitingPerMin(domain.getWaitingPerMin());
        entity.setNightSurchargePct(domain.getNightSurchargePct());
        entity.setAirportSurcharge(domain.getAirportSurcharge());
        entity.setValidFrom(domain.getValidFrom());
        entity.setValidTo(domain.getValidTo());
        entity.setActive(domain.getActive());
        return entity;
    }

    default Tariff toDomain(TariffEntity entity) {
        if (entity == null) {
            return null;
        }
        return Tariff.reconstruct(
                entity.getId(),
                entity.getName(),
                entity.getZoneId(),
                entity.getVehicleType(),
                entity.getBaseFare(),
                entity.getPerKm(),
                entity.getPerMin(),
                entity.getMinFare(),
                entity.getWaitingPerMin(),
                entity.getNightSurchargePct(),
                entity.getAirportSurcharge(),
                entity.getValidFrom(),
                entity.getValidTo(),
                entity.getActive()
        );
    }
}
