package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity.BookingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingsRepository extends JpaRepository<BookingsEntity, UUID> {
}
