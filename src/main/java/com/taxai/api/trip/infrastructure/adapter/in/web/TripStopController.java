package com.taxai.api.trip.infrastructure.adapter.in.web;

import com.taxai.api.trip.application.port.in.AddTripStopUseCase;
import com.taxai.api.trip.application.port.in.ListTripStopsUseCase;
import com.taxai.api.trip.application.port.in.UpdateTripStopUseCase;
import com.taxai.api.trip.application.result.ListTripStopsResult;
import com.taxai.api.trip.application.result.TripStopResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.AddTripStopHttpRequest;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.UpdateTripStopHttpRequest;
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
@RequestMapping("/v1/trips/{id}/stops")
@Tag(name = "Trip Stop Management", description = "API for managing trip intermediate stops and waypoints.")
public class TripStopController {

    private final TripWebMapper mapper;

    private final AddTripStopUseCase addTripStopUseCase;
    private final ListTripStopsUseCase listTripStopsUseCase;
    private final UpdateTripStopUseCase updateTripStopUseCase;

    @PostMapping
    @Operation(
            summary = "Add trip stop",
            description = "Adds a pickup, dropoff or intermediate stop to a trip."
    )
    public ResponseEntity<TripStopResult> addTripStop(
            @PathVariable UUID id,
            @Valid @RequestBody AddTripStopHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(addTripStopUseCase.execute(mapper.toAddTripStopCommand(id, request)));
    }

    @GetMapping
    @Operation(
            summary = "List trip stops",
            description = "Retrieves all stops configured for a trip in sequence."
    )
    public ResponseEntity<ListTripStopsResult> listTripStops(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(listTripStopsUseCase.execute(mapper.toListTripStopsCommand(id)));
    }

    @PatchMapping("/{stopId}")
    @Operation(
            summary = "Update trip stop status",
            description = "Updates status or completion details of a specific trip stop."
    )
    public ResponseEntity<TripStopResult> updateTripStop(
            @PathVariable UUID id,
            @PathVariable UUID stopId,
            @Valid @RequestBody UpdateTripStopHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateTripStopUseCase.execute(mapper.toUpdateTripStopCommand(id, stopId, request)));
    }
}
