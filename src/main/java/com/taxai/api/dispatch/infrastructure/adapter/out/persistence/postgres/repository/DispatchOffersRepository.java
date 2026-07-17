package com.taxai.api.dispatch.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.dispatch.infrastructure.adapter.out.persistence.postgres.entity.DispatchOffersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DispatchOffersRepository extends JpaRepository<DispatchOffersEntity, UUID> {
}
