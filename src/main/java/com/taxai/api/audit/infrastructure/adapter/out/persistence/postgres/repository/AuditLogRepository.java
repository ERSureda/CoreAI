package com.taxai.api.audit.infrastructure.adapter.out.persistence.postgres.repository;

import com.taxai.api.audit.infrastructure.adapter.out.persistence.postgres.entity.AuditLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
}
