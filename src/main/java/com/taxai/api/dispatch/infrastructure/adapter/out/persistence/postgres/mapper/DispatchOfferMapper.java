package com.taxai.api.dispatch.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.dispatch.domain.model.DispatchOffer;
import com.taxai.api.dispatch.infrastructure.adapter.out.persistence.postgres.entity.DispatchOfferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface DispatchOfferMapper {

    default DispatchOfferEntity toEntity(DispatchOffer domain) {
        if (domain == null) {
            return null;
        }
        DispatchOfferEntity entity = new DispatchOfferEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setDriverId(domain.getDriverId());
        entity.setWave(domain.getWave());
        entity.setRank(domain.getRank());
        entity.setDistanceM(domain.getDistanceM());
        entity.setEtaS(domain.getEtaS());
        entity.setOfferedAt(domain.getOfferedAt());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setStatus(domain.getStatus());
        entity.setRespondedAt(domain.getRespondedAt());
        return entity;
    }

    default DispatchOffer toDomain(DispatchOfferEntity entity) {
        if (entity == null) {
            return null;
        }
        return DispatchOffer.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getDriverId(),
                entity.getWave(),
                entity.getRank(),
                entity.getDistanceM(),
                entity.getEtaS(),
                entity.getOfferedAt(),
                entity.getExpiresAt(),
                entity.getStatus(),
                entity.getRespondedAt()
        );
    }
}
