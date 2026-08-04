package com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.trip.infrastructure.adapter.out.persistence.postgres.entity.TripStatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripStatusHistoryRepository extends JpaRepository<TripStatusHistoryEntity, Long> {
}
