package com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.entity.IncidentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncidentsRepository extends JpaRepository<IncidentsEntity, UUID> {
}
