package com.taxai.api.trip.infrastructure.adapter.in.web;

import com.taxai.api.trip.application.port.in.AddTripRouteUseCase;
import com.taxai.api.trip.application.port.in.GetActiveTripRouteUseCase;
import com.taxai.api.trip.application.result.TripRouteResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.AddTripRouteHttpRequest;
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
@RequestMapping("/v1/trips/{id}/routes")
@Tag(name = "Trip Route Management", description = "API for computed trip routes and polyline navigation.")
public class TripRouteController {

    private final TripWebMapper mapper;

    private final AddTripRouteUseCase addTripRouteUseCase;
    private final GetActiveTripRouteUseCase getActiveTripRouteUseCase;

    @PostMapping
    @Operation(
            summary = "Add calculated route",
            description = "Stores a new calculated route polyline and distance/duration metrics for a trip."
    )
    public ResponseEntity<TripRouteResult> addTripRoute(
            @PathVariable UUID id,
            @Valid @RequestBody AddTripRouteHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(addTripRouteUseCase.execute(id, request));
    }

    @GetMapping("/active")
    @Operation(
            summary = "Get active trip route",
            description = "Retrieves the latest active navigation route for a trip."
    )
    public ResponseEntity<TripRouteResult> getActiveTripRoute(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getActiveTripRouteUseCase.execute(id));
    }
}
