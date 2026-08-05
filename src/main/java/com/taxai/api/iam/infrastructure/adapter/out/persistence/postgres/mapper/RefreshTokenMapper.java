package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.iam.domain.model.RefreshToken;
import com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity.RefreshTokenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RefreshTokenMapper {

    default RefreshTokenEntity toEntity(RefreshToken domain) {
        if (domain == null) {
            return null;
        }
        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setId(domain.getId());
        entity.setUserId(domain.getUserId());
        entity.setTokenHash(domain.getTokenHash());
        entity.setExpiresAt(domain.getExpiresAt());
        entity.setRevokedAt(domain.getRevokedAt());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setCreatedIp(domain.getCreatedIp());
        entity.setUserAgent(domain.getUserAgent());
        return entity;
    }

    default RefreshToken toDomain(RefreshTokenEntity entity) {
        if (entity == null) {
            return null;
        }
        return RefreshToken.reconstruct(
                entity.getId(),
                entity.getUserId(),
                entity.getTokenHash(),
                entity.getExpiresAt(),
                entity.getRevokedAt(),
                entity.getCreatedAt(),
                entity.getCreatedIp(),
                entity.getUserAgent()
        );
    }
}
