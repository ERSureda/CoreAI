package com.taxai.api.audit.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.audit.domain.model.AuditLog;
import com.taxai.api.audit.infrastructure.adapter.out.persistence.postgres.entity.AuditLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuditLogMapper {

    default AuditLogEntity toEntity(AuditLog domain) {
        if (domain == null) {
            return null;
        }
        AuditLogEntity entity = new AuditLogEntity();
        entity.setId(domain.getId());
        entity.setOccurredAt(domain.getOccurredAt());
        entity.setAggregateType(domain.getAggregateType());
        entity.setAggregateId(domain.getAggregateId());
        entity.setAction(domain.getAction());
        entity.setActorType(domain.getActorType());
        entity.setActorId(domain.getActorId());
        entity.setSource(domain.getSource());
        entity.setEventId(domain.getEventId());
        return entity;
    }

    default AuditLog toDomain(AuditLogEntity entity) {
        if (entity == null) {
            return null;
        }
        return AuditLog.reconstruct(
                entity.getId(),
                entity.getOccurredAt(),
                entity.getAggregateType(),
                entity.getAggregateId(),
                entity.getAction(),
                entity.getActorType(),
                entity.getActorId(),
                entity.getSource(),
                entity.getEventId()
        );
    }
}
