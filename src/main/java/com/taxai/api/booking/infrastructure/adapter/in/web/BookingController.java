package com.taxai.api.booking.infrastructure.adapter.in.web;

import com.taxai.api.booking.application.port.in.*;
import com.taxai.api.booking.application.result.*;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.*;
import com.taxai.api.booking.infrastructure.adapter.in.web.mapper.BookingWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bookings")
@Tag(name = "Booking Management", description = "API for ride bookings, schedule management, luggage and pet add-ons.")
public class BookingController {

    private final BookingWebMapper mapper;

    private final CreateBookingUseCase createBookingUseCase;
    private final GetBookingUseCase getBookingUseCase;
    private final GetBookingByLocatorUseCase getBookingByLocatorUseCase;
    private final ListBookingsUseCase listBookingsUseCase;
    private final UpdateBookingUseCase updateBookingUseCase;
    private final ConfirmBookingUseCase confirmBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final FulfillBookingUseCase fulfillBookingUseCase;
    private final AddLuggageUseCase addLuggageUseCase;
    private final RemoveLuggageUseCase removeLuggageUseCase;
    private final AddPetUseCase addPetUseCase;
    private final RemovePetUseCase removePetUseCase;

    @PostMapping
    @Operation(
            summary = "Create booking",
            description = "Creates a new ride booking request."
    )
    public ResponseEntity<BookingResult> createBooking(@Valid @RequestBody CreateBookingHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createBookingUseCase.execute(mapper.toCreateBookingCommand(request)));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get booking by ID",
            description = "Retrieves booking details by ID."
    )
    public ResponseEntity<BookingResult> getBooking(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getBookingUseCase.execute(mapper.toGetBookingCommand(id)));
    }

    @GetMapping("/locator/{locator}")
    @Operation(
            summary = "Get booking by locator",
            description = "Retrieves booking details by unique alphanumeric locator code."
    )
    public ResponseEntity<BookingResult> getBookingByLocator(@PathVariable String locator) {
        return ResponseEntity
                .status(200)
                .body(getBookingByLocatorUseCase.execute(mapper.toGetBookingByLocatorCommand(locator)));
    }

    @GetMapping
    @Operation(
            summary = "List bookings",
            description = "Retrieves a paginated list of bookings filtered by status, passenger or tenant."
    )
    public ResponseEntity<ListBookingsResult> listBookings(@Valid ListBookingsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listBookingsUseCase.execute(mapper.toListBookingsCommand(request)));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update booking",
            description = "Modifies scheduled pickup time, vehicle requirements or notes of a booking."
    )
    public ResponseEntity<BookingResult> updateBooking(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBookingHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateBookingUseCase.execute(mapper.toUpdateBookingCommand(id, request)));
    }

    @PostMapping("/{id}/confirm")
    @Operation(
            summary = "Confirm booking",
            description = "Confirms a pending booking."
    )
    public ResponseEntity<BookingResult> confirmBooking(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(confirmBookingUseCase.execute(mapper.toConfirmBookingCommand(id)));
    }

    @PostMapping("/{id}/cancel")
    @Operation(
            summary = "Cancel booking",
            description = "Cancels a booking with reason."
    )
    public ResponseEntity<BookingResult> cancelBooking(
            @PathVariable UUID id,
            @Valid @RequestBody CancelBookingHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(cancelBookingUseCase.execute(mapper.toCancelBookingCommand(id, request)));
    }

    @PostMapping("/{id}/fulfill")
    @Operation(
            summary = "Fulfill booking",
            description = "Marks booking as fulfilled upon successful trip completion."
    )
    public ResponseEntity<BookingResult> fulfillBooking(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(fulfillBookingUseCase.execute(mapper.toFulfillBookingCommand(id)));
    }

    @PostMapping("/{bookingId}/luggage")
    @Operation(
            summary = "Add luggage to booking",
            description = "Adds a luggage item requirement to a booking."
    )
    public ResponseEntity<LuggageResult> addLuggage(
            @PathVariable UUID bookingId,
            @Valid @RequestBody AddLuggageHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(addLuggageUseCase.execute(mapper.toAddLuggageCommand(bookingId, request)));
    }

    @DeleteMapping("/{bookingId}/luggage/{luggageId}")
    @Operation(
            summary = "Remove luggage from booking",
            description = "Removes a luggage item from a booking."
    )
    public ResponseEntity<Void> removeLuggage(
            @PathVariable UUID bookingId,
            @PathVariable UUID luggageId
    ) {
        removeLuggageUseCase.execute(mapper.toRemoveLuggageCommand(bookingId, luggageId));
        return ResponseEntity
                .status(200)
                .build();
    }

    @PostMapping("/{bookingId}/pets")
    @Operation(
            summary = "Add pet to booking",
            description = "Adds a pet requirement to a booking."
    )
    public ResponseEntity<PetResult> addPet(
            @PathVariable UUID bookingId,
            @Valid @RequestBody AddPetHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(addPetUseCase.execute(mapper.toAddPetCommand(bookingId, request)));
    }

    @DeleteMapping("/{bookingId}/pets/{petId}")
    @Operation(
            summary = "Remove pet from booking",
            description = "Removes a pet requirement from a booking."
    )
    public ResponseEntity<Void> removePet(
            @PathVariable UUID bookingId,
            @PathVariable UUID petId
    ) {
        removePetUseCase.execute(mapper.toRemovePetCommand(bookingId, petId));
        return ResponseEntity
                .status(200)
                .build();
    }
}
