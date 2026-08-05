package com.taxai.api.audit.infrastructure.adapter.in.web.mapper;

import com.taxai.api.audit.application.command.*;
import com.taxai.api.audit.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AuditWebMapper {

    ListAuditLogsCommand toListAuditLogsCommand(ListAuditLogsHttpRequest request);

    GetAuditLogCommand toGetAuditLogCommand(UUID id);
}
