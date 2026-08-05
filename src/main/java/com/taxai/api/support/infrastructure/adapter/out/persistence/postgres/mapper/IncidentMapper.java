package com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.mapper;

import com.taxai.api.support.domain.model.Incident;
import com.taxai.api.support.infrastructure.adapter.out.persistence.postgres.entity.IncidentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IncidentMapper {

    default IncidentEntity toEntity(Incident domain) {
        if (domain == null) {
            return null;
        }
        IncidentEntity entity = new IncidentEntity();
        entity.setId(domain.getId());
        entity.setTripId(domain.getTripId());
        entity.setType(domain.getType());
        entity.setStatus(domain.getStatus());
        entity.setPriority(domain.getPriority());
        entity.setReportedBy(domain.getReportedBy());
        entity.setReporterId(domain.getReporterId());
        entity.setAssigneeId(domain.getAssigneeId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setResolution(domain.getResolution());
        entity.setSourceEventId(domain.getSourceEventId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setResolvedAt(domain.getResolvedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        return entity;
    }

    default Incident toDomain(IncidentEntity entity) {
        if (entity == null) {
            return null;
        }
        return Incident.reconstruct(
                entity.getId(),
                entity.getTripId(),
                entity.getType(),
                entity.getStatus(),
                entity.getPriority(),
                entity.getReportedBy(),
                entity.getReporterId(),
                entity.getAssigneeId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getResolution(),
                entity.getSourceEventId(),
                entity.getCreatedAt(),
                entity.getResolvedAt(),
                entity.getUpdatedAt()
        );
    }
}
