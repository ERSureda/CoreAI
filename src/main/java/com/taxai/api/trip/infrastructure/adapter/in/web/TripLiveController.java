package com.taxai.api.trip.infrastructure.adapter.in.web;

import com.taxai.api.trip.application.port.in.StreamTenantTripsLiveUseCase;
import com.taxai.api.trip.application.port.in.StreamTripLiveUseCase;
import com.taxai.api.trip.infrastructure.adapter.in.web.mapper.TripWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Tag(name = "Trip Live Stream", description = "Server-Sent Events (SSE) streaming API for live trip location and status updates.")
public class TripLiveController {

    private final TripWebMapper mapper;

    private final StreamTripLiveUseCase streamTripLiveUseCase;
    private final StreamTenantTripsLiveUseCase streamTenantTripsLiveUseCase;

    @GetMapping("/v1/trips/{id}/live")
    @Operation(
            summary = "Stream live trip updates",
            description = "Establishes SSE stream for real-time ETA, location coordinates and status updates for a single trip."
    )
    public ResponseEntity<SseEmitter> streamTripLive(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(streamTripLiveUseCase.execute(id));
    }

    @GetMapping("/v1/tenants/{id}/trips/live")
    @Operation(
            summary = "Stream live tenant trips fleet updates",
            description = "Establishes SSE stream for real-time overview of active trips across a tenant fleet."
    )
    public ResponseEntity<SseEmitter> streamTenantTripsLive(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(streamTenantTripsLiveUseCase.execute(id));
    }
}
