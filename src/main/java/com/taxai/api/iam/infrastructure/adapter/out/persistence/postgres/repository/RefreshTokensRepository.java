package com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.iam.infrastructure.adapter.out.persistence.postgres.entity.RefreshTokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokensRepository extends JpaRepository<RefreshTokensEntity, UUID> {
}
