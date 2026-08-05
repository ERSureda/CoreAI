package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.iam.domain.model.Credential;
import com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity.CredentialEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CredentialMapper {

    default CredentialEntity toEntity(Credential domain) {
        if (domain == null) {
            return null;
        }
        CredentialEntity entity = new CredentialEntity();
        entity.setUserId(domain.getId());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setFailedLoginCount(domain.getFailedLoginCount());
        entity.setLockedUntil(domain.getLockedUntil());
        entity.setLastLoginAt(domain.getLastLoginAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Credential toDomain(CredentialEntity entity) {
        if (entity == null) {
            return null;
        }
        return Credential.reconstruct(
                entity.getUserId(),
                entity.getPasswordHash(),
                entity.getFailedLoginCount(),
                entity.getLockedUntil(),
                entity.getLastLoginAt(),
                entity.getUpdatedAt()
        );
    }
}
