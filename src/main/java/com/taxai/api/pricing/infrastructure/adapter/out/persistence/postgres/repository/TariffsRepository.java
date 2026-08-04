package com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.pricing.infrastructure.adapter.out.persistence.postgres.entity.TariffsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TariffsRepository extends JpaRepository<TariffsEntity, UUID> {
}
