package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {
}
