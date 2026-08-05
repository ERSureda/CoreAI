package com.taxai.api.dispatch.infrastructure.adapter.in.web.mapper;

import com.taxai.api.dispatch.application.command.*;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DispatchWebMapper {

    MatchTripCommand toMatchTripCommand(UUID tripId);

    @Mapping(target = "tripId", source = "tripId")
    AssignNearestDriverCommand toAssignNearestDriverCommand(UUID tripId, AssignNearestDriverHttpRequest request);

    @Mapping(target = "tripId", source = "tripId")
    ListTripOffersCommand toListTripOffersCommand(UUID tripId, ListTripOffersHttpRequest request);

    ListPendingDriverOffersCommand toListPendingDriverOffersCommand(UUID driverId);

    AcceptOfferCommand toAcceptOfferCommand(UUID offerId);

    RejectOfferCommand toRejectOfferCommand(UUID offerId);

    @Mapping(target = "driverId", source = "driverId")
    AcceptOfferViaChannelCommand toAcceptOfferViaChannelCommand(UUID driverId, AcceptOfferViaChannelHttpRequest request);

    @Mapping(target = "driverId", source = "driverId")
    RejectOfferViaChannelCommand toRejectOfferViaChannelCommand(UUID driverId, RejectOfferViaChannelHttpRequest request);

    @Mapping(target = "driverId", source = "driverId")
    UpdateDriverLocationCommand toUpdateDriverLocationCommand(UUID driverId, UpdateDriverLocationHttpRequest request);
}
