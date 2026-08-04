package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity.BookingPetsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingPetsRepository extends JpaRepository<BookingPetsEntity, UUID> {
}
