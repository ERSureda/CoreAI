package com.taxai.api.trip.infrastructure.adapter.in.web.mapper;

import com.taxai.api.trip.application.command.*;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TripWebMapper {

    CreateTripCommand toCreateTripCommand(CreateTripHttpRequest request);

    GetTripCommand toGetTripCommand(UUID id);

    ListTripsCommand toListTripsCommand(ListTripsHttpRequest request);

    GetCurrentTripByDriverCommand toGetCurrentTripByDriverCommand(UUID driverId);

    StartTripSearchCommand toStartTripSearchCommand(UUID id);

    @Mapping(target = "id", source = "id")
    AssignTripCommand toAssignTripCommand(UUID id, AssignTripHttpRequest request);

    AcceptTripCommand toAcceptTripCommand(UUID id);

    MarkTripArrivingCommand toMarkTripArrivingCommand(UUID id);

    MarkTripArrivedCommand toMarkTripArrivedCommand(UUID id);

    BoardTripCommand toBoardTripCommand(UUID id);

    @Mapping(target = "id", source = "id")
    CompleteTripCommand toCompleteTripCommand(UUID id, CompleteTripHttpRequest request);

    @Mapping(target = "id", source = "id")
    CancelTripCommand toCancelTripCommand(UUID id, CancelTripHttpRequest request);

    @Mapping(target = "id", source = "id")
    FailTripCommand toFailTripCommand(UUID id, FailTripHttpRequest request);

    GetTripStatusHistoryCommand toGetTripStatusHistoryCommand(UUID id);

    @Mapping(target = "tripId", source = "tripId")
    AddTripStopCommand toAddTripStopCommand(UUID tripId, AddTripStopHttpRequest request);

    ListTripStopsCommand toListTripStopsCommand(UUID tripId);

    @Mapping(target = "tripId", source = "tripId")
    @Mapping(target = "stopId", source = "stopId")
    UpdateTripStopCommand toUpdateTripStopCommand(UUID tripId, UUID stopId, UpdateTripStopHttpRequest request);

    @Mapping(target = "tripId", source = "tripId")
    AddTripRouteCommand toAddTripRouteCommand(UUID tripId, AddTripRouteHttpRequest request);

    GetActiveTripRouteCommand toGetActiveTripRouteCommand(UUID tripId);

    StreamTripLiveCommand toStreamTripLiveCommand(UUID tripId);

    StreamTenantTripsLiveCommand toStreamTenantTripsLiveCommand(UUID tenantId);
}
