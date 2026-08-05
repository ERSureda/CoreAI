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
@Tag(name = "Booking Management", description = "API for booking creation, lifecycle management, luggage and pets.")
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
            summary = "Create a booking",
            description = "Creates a new ride booking with passenger count, optional scheduled time, luggage and pet requirements."
    )
    public ResponseEntity<BookingResult> createBooking(@Valid @RequestBody CreateBookingHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createBookingUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get booking by ID",
            description = "Retrieves full details of a specific booking."
    )
    public ResponseEntity<BookingResult> getBooking(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getBookingUseCase.execute(id));
    }

    @GetMapping("/by-locator/{locator}")
    @Operation(
            summary = "Get booking by locator",
            description = "Retrieves booking details using the unique alphanumeric locator code."
    )
    public ResponseEntity<BookingResult> getBookingByLocator(@PathVariable String locator) {
        return ResponseEntity
                .status(200)
                .body(getBookingByLocatorUseCase.execute(locator));
    }

    @GetMapping
    @Operation(
            summary = "List bookings",
            description = "Retrieves a paginated list of bookings filtered by tenant, status or passenger."
    )
    public ResponseEntity<ListBookingsResult> listBookings(@Valid ListBookingsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listBookingsUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update booking details",
            description = "Partially updates pickup schedule, vehicle requirements or notes for an existing booking."
    )
    public ResponseEntity<BookingResult> updateBooking(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateBookingHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateBookingUseCase.execute(id, request));
    }

    @PostMapping("/{id}/confirm")
    @Operation(
            summary = "Confirm booking",
            description = "Confirms a pending booking."
    )
    public ResponseEntity<BookingResult> confirmBooking(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(confirmBookingUseCase.execute(id));
    }

    @PostMapping("/{id}/cancel")
    @Operation(
            summary = "Cancel booking",
            description = "Cancels an existing booking with an optional reason."
    )
    public ResponseEntity<BookingResult> cancelBooking(
            @PathVariable UUID id,
            @RequestBody(required = false) CancelBookingHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(cancelBookingUseCase.execute(id, request));
    }

    @PostMapping("/{id}/fulfill")
    @Operation(
            summary = "Fulfill booking",
            description = "Marks a booking as fulfilled upon successful trip completion."
    )
    public ResponseEntity<BookingResult> fulfillBooking(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(fulfillBookingUseCase.execute(id));
    }

    @PostMapping("/{id}/luggage")
    @Operation(
            summary = "Add luggage to booking",
            description = "Adds a luggage item specification to an existing booking."
    )
    public ResponseEntity<LuggageResult> addLuggage(
            @PathVariable UUID id,
            @Valid @RequestBody AddLuggageHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(addLuggageUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}/luggage/{luggageId}")
    @Operation(
            summary = "Remove luggage from booking",
            description = "Removes a luggage item from a booking."
    )
    public ResponseEntity<Void> removeLuggage(
            @PathVariable UUID id,
            @PathVariable UUID luggageId
    ) {
        removeLuggageUseCase.execute(id, luggageId);
        return ResponseEntity
                .status(204)
                .build();
    }

    @PostMapping("/{id}/pets")
    @Operation(
            summary = "Add pet to booking",
            description = "Adds a pet specification to an existing booking."
    )
    public ResponseEntity<PetResult> addPet(
            @PathVariable UUID id,
            @Valid @RequestBody AddPetHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(addPetUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}/pets/{petId}")
    @Operation(
            summary = "Remove pet from booking",
            description = "Removes a pet specification from a booking."
    )
    public ResponseEntity<Void> removePet(
            @PathVariable UUID id,
            @PathVariable UUID petId
    ) {
        removePetUseCase.execute(id, petId);
        return ResponseEntity
                .status(204)
                .build();
    }
}
