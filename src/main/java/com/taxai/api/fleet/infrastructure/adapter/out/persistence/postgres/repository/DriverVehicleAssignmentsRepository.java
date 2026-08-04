package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity.DriverVehicleAssignmentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriverVehicleAssignmentsRepository extends JpaRepository<DriverVehicleAssignmentsEntity, UUID> {
}
