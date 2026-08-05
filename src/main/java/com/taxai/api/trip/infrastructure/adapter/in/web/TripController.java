package com.taxai.api.trip.infrastructure.adapter.in.web;

import com.taxai.api.trip.application.port.in.*;
import com.taxai.api.trip.application.result.*;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.*;
import com.taxai.api.trip.infrastructure.adapter.in.web.mapper.TripWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/trips")
@Tag(name = "Trip Management", description = "API for trip execution, status transitions and tracking.")
public class TripController {

    private final TripWebMapper mapper;

    private final CreateTripUseCase createTripUseCase;
    private final GetTripUseCase getTripUseCase;
    private final ListTripsUseCase listTripsUseCase;
    private final GetCurrentTripByDriverUseCase getCurrentTripByDriverUseCase;
    private final StartTripSearchUseCase startTripSearchUseCase;
    private final AssignTripUseCase assignTripUseCase;
    private final AcceptTripUseCase acceptTripUseCase;
    private final MarkTripArrivingUseCase markTripArrivingUseCase;
    private final MarkTripArrivedUseCase markTripArrivedUseCase;
    private final BoardTripUseCase boardTripUseCase;
    private final CompleteTripUseCase completeTripUseCase;
    private final CancelTripUseCase cancelTripUseCase;
    private final FailTripUseCase failTripUseCase;
    private final GetTripStatusHistoryUseCase getTripStatusHistoryUseCase;

    @PostMapping
    @Operation(
            summary = "Create trip",
            description = "Creates a new trip instance linked to a booking."
    )
    public ResponseEntity<TripResult> createTrip(@Valid @RequestBody CreateTripHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createTripUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get trip by ID",
            description = "Retrieves current status and details of a trip."
    )
    public ResponseEntity<TripResult> getTrip(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getTripUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List trips",
            description = "Retrieves a paginated list of trips filtered by tenant, status or driver."
    )
    public ResponseEntity<ListTripsResult> listTrips(@Valid ListTripsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listTripsUseCase.execute(request));
    }

    @GetMapping("/driver/{driverId}/current")
    @Operation(
            summary = "Get current trip by driver",
            description = "Retrieves the currently assigned active trip for a given driver."
    )
    public ResponseEntity<TripResult> getCurrentTripByDriver(@PathVariable UUID driverId) {
        return ResponseEntity
                .status(200)
                .body(getCurrentTripByDriverUseCase.execute(driverId));
    }

    @PostMapping("/{id}/search")
    @Operation(
            summary = "Start trip driver search",
            description = "Initiates automated driver dispatch search for a trip."
    )
    public ResponseEntity<TripResult> startTripSearch(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(startTripSearchUseCase.execute(id));
    }

    @PostMapping("/{id}/assign")
    @Operation(
            summary = "Manually assign trip",
            description = "Manually assigns a specified driver and vehicle to a trip."
    )
    public ResponseEntity<TripResult> assignTrip(
            @PathVariable UUID id,
            @Valid @RequestBody AssignTripHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(assignTripUseCase.execute(id, request));
    }

    @PostMapping("/{id}/accept")
    @Operation(
            summary = "Accept trip offer",
            description = "Marks trip as accepted by assigned driver."
    )
    public ResponseEntity<TripResult> acceptTrip(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(acceptTripUseCase.execute(id));
    }

    @PostMapping("/{id}/arriving")
    @Operation(
            summary = "Mark trip arriving",
            description = "Updates trip status indicating driver is en route to pickup point."
    )
    public ResponseEntity<TripResult> markTripArriving(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(markTripArrivingUseCase.execute(id));
    }

    @PostMapping("/{id}/arrived")
    @Operation(
            summary = "Mark trip arrived",
            description = "Updates trip status indicating driver has arrived at pickup point."
    )
    public ResponseEntity<TripResult> markTripArrived(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(markTripArrivedUseCase.execute(id));
    }

    @PostMapping("/{id}/board")
    @Operation(
            summary = "Board trip",
            description = "Marks passenger as boarded and starts in-progress trip phase."
    )
    public ResponseEntity<TripResult> boardTrip(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(boardTripUseCase.execute(id));
    }

    @PostMapping("/{id}/complete")
    @Operation(
            summary = "Complete trip",
            description = "Completes the trip and records actual distance and duration."
    )
    public ResponseEntity<TripResult> completeTrip(
            @PathVariable UUID id,
            @Valid @RequestBody CompleteTripHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(completeTripUseCase.execute(id, request));
    }

    @PostMapping("/{id}/cancel")
    @Operation(
            summary = "Cancel trip",
            description = "Cancels an active or assigned trip."
    )
    public ResponseEntity<TripResult> cancelTrip(
            @PathVariable UUID id,
            @Valid @RequestBody CancelTripHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(cancelTripUseCase.execute(id, request));
    }

    @PostMapping("/{id}/fail")
    @Operation(
            summary = "Fail trip",
            description = "Marks a trip as failed due to unresolvable errors or dispatch timeout."
    )
    public ResponseEntity<TripResult> failTrip(
            @PathVariable UUID id,
            @Valid @RequestBody FailTripHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(failTripUseCase.execute(id, request));
    }

    @GetMapping("/{id}/status-history")
    @Operation(
            summary = "Get trip status history",
            description = "Retrieves full chronological state transition history of a trip."
    )
    public ResponseEntity<TripStatusHistoryResult> getTripStatusHistory(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getTripStatusHistoryUseCase.execute(id));
    }
}
