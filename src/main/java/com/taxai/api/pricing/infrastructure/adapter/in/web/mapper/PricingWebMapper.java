package com.taxai.api.pricing.infrastructure.adapter.in.web.mapper;

import com.taxai.api.pricing.application.command.*;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PricingWebMapper {

    CreateFareEstimateCommand toCreateFareEstimateCommand(CreateFareEstimateHttpRequest request);

    ListBookingFareEstimatesCommand toListBookingFareEstimatesCommand(UUID bookingId);

    @Mapping(target = "tripId", source = "tripId")
    CreateTripReceiptCommand toCreateTripReceiptCommand(UUID tripId, CreateTripReceiptHttpRequest request);

    GetTripReceiptCommand toGetTripReceiptCommand(UUID tripId);

    ListReceiptsCommand toListReceiptsCommand(ListReceiptsHttpRequest request);

    CreateTariffCommand toCreateTariffCommand(CreateTariffHttpRequest request);

    GetTariffCommand toGetTariffCommand(UUID id);

    ListTariffsCommand toListTariffsCommand(ListTariffsHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateTariffCommand toUpdateTariffCommand(UUID id, UpdateTariffHttpRequest request);

    DeleteTariffCommand toDeleteTariffCommand(UUID id);
}
