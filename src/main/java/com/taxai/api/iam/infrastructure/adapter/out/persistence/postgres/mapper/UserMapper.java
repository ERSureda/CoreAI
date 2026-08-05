package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.iam.domain.model.User;
import com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserMapper {

    default UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setPhone(domain.getPhone());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return User.reconstruct(
                entity.getId(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
