package com.taxai.api.fleet.infrastructure.adapter.in.web.mapper;

import com.taxai.api.fleet.application.command.*;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface FleetWebMapper {

    CreateTenantCommand toCreateTenantCommand(CreateTenantHttpRequest request);

    GetTenantCommand toGetTenantCommand(UUID id);

    @Mapping(target = "id", source = "id")
    UpdateTenantCommand toUpdateTenantCommand(UUID id, UpdateTenantHttpRequest request);

    @Mapping(target = "tenantId", source = "tenantId")
    CreateOperatorCommand toCreateOperatorCommand(UUID tenantId, CreateOperatorHttpRequest request);

    GetOperatorCommand toGetOperatorCommand(UUID id);

    ListOperatorsCommand toListOperatorsCommand(ListOperatorsHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateOperatorCommand toUpdateOperatorCommand(UUID id, UpdateOperatorHttpRequest request);

    @Mapping(target = "tenantId", source = "tenantId")
    CreateVehicleCommand toCreateVehicleCommand(UUID tenantId, CreateVehicleHttpRequest request);

    GetVehicleCommand toGetVehicleCommand(UUID id);

    ListVehiclesCommand toListVehiclesCommand(ListVehiclesHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateVehicleCommand toUpdateVehicleCommand(UUID id, UpdateVehicleHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateVehicleStatusCommand toUpdateVehicleStatusCommand(UUID id, UpdateVehicleStatusHttpRequest request);

    @Mapping(target = "tenantId", source = "tenantId")
    CreateDriverCommand toCreateDriverCommand(UUID tenantId, CreateDriverHttpRequest request);

    GetDriverCommand toGetDriverCommand(UUID id);

    ListDriversCommand toListDriversCommand(ListDriversHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateDriverCommand toUpdateDriverCommand(UUID id, UpdateDriverHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateDriverStatusCommand toUpdateDriverStatusCommand(UUID id, UpdateDriverStatusHttpRequest request);

    @Mapping(target = "driverId", source = "driverId")
    CreateVehicleAssignmentCommand toCreateVehicleAssignmentCommand(UUID driverId, CreateVehicleAssignmentHttpRequest request);

    EndVehicleAssignmentCommand toEndVehicleAssignmentCommand(UUID id);

    GetCurrentVehicleAssignmentCommand toGetCurrentVehicleAssignmentCommand(UUID driverId);

    @Mapping(target = "driverId", source = "driverId")
    UpdateDriverPresenceCommand toUpdateDriverPresenceCommand(UUID driverId, UpdateDriverPresenceHttpRequest request);
}
