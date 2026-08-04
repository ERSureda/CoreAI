package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity.TripsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TripsRepository extends JpaRepository<TripsEntity, UUID> {
}
