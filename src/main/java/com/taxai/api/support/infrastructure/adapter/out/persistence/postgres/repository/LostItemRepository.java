package com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.entity.LostItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LostItemRepository extends JpaRepository<LostItemEntity, UUID> {
}
