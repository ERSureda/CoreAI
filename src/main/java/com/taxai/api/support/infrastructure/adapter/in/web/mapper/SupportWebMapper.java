package com.taxai.api.support.infrastructure.adapter.in.web.mapper;

import com.taxai.api.support.application.command.*;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface SupportWebMapper {

    CreateIncidentCommand toCreateIncidentCommand(CreateIncidentHttpRequest request);

    GetIncidentCommand toGetIncidentCommand(UUID id);

    ListIncidentsCommand toListIncidentsCommand(ListIncidentsHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateIncidentCommand toUpdateIncidentCommand(UUID id, UpdateIncidentHttpRequest request);

    @Mapping(target = "id", source = "id")
    AssignIncidentCommand toAssignIncidentCommand(UUID id, AssignIncidentHttpRequest request);

    @Mapping(target = "id", source = "id")
    ResolveIncidentCommand toResolveIncidentCommand(UUID id, ResolveIncidentHttpRequest request);

    CloseIncidentCommand toCloseIncidentCommand(UUID id);

    CreateLostItemCommand toCreateLostItemCommand(CreateLostItemHttpRequest request);

    GetLostItemCommand toGetLostItemCommand(UUID id);

    ListLostItemsCommand toListLostItemsCommand(ListLostItemsHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateLostItemStatusCommand toUpdateLostItemStatusCommand(UUID id, UpdateLostItemStatusHttpRequest request);
}
