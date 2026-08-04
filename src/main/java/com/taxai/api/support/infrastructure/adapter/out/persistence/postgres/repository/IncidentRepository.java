package com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.entity.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncidentRepository extends JpaRepository<IncidentEntity, UUID> {
}
