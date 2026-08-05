package com.taxai.api.booking.infrastructure.adapter.in.web.mapper;

import com.taxai.api.booking.application.command.*;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BookingWebMapper {

    CreateBookingCommand toCreateBookingCommand(CreateBookingHttpRequest request);

    GetBookingCommand toGetBookingCommand(UUID id);

    GetBookingByLocatorCommand toGetBookingByLocatorCommand(String locator);

    ListBookingsCommand toListBookingsCommand(ListBookingsHttpRequest request);

    @Mapping(target = "id", source = "id")
    UpdateBookingCommand toUpdateBookingCommand(UUID id, UpdateBookingHttpRequest request);

    ConfirmBookingCommand toConfirmBookingCommand(UUID id);

    @Mapping(target = "id", source = "id")
    CancelBookingCommand toCancelBookingCommand(UUID id, CancelBookingHttpRequest request);

    FulfillBookingCommand toFulfillBookingCommand(UUID id);

    @Mapping(target = "bookingId", source = "bookingId")
    AddLuggageCommand toAddLuggageCommand(UUID bookingId, AddLuggageHttpRequest request);

    RemoveLuggageCommand toRemoveLuggageCommand(UUID bookingId, UUID luggageId);

    @Mapping(target = "bookingId", source = "bookingId")
    AddPetCommand toAddPetCommand(UUID bookingId, AddPetHttpRequest request);

    RemovePetCommand toRemovePetCommand(UUID bookingId, UUID petId);
}
