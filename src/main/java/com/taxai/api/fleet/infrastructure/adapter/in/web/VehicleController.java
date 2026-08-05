package com.taxai.api.fleet.infrastructure.adapter.in.web;

import com.taxai.api.fleet.application.port.in.*;
import com.taxai.api.fleet.application.result.ListVehiclesResult;
import com.taxai.api.fleet.application.result.VehicleResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.*;
import com.taxai.api.fleet.infrastructure.adapter.in.web.mapper.FleetWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/fleet/vehicles")
@Tag(name = "Vehicle Management", description = "API for vehicle fleet management.")
public class VehicleController {

    private final FleetWebMapper mapper;

    private final CreateVehicleUseCase createVehicleUseCase;
    private final GetVehicleUseCase getVehicleUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;

    @PostMapping
    @Operation(
            summary = "Register new vehicle",
            description = "Registers a new vehicle in the fleet with its physical attributes and features."
    )
    public ResponseEntity<VehicleResult> createVehicle(@Valid @RequestBody CreateVehicleHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createVehicleUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get vehicle by ID",
            description = "Retrieves full details of a specific vehicle."
    )
    public ResponseEntity<VehicleResult> getVehicle(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getVehicleUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List vehicles",
            description = "Retrieves a paginated list of vehicles filtered by tenant, type, or status."
    )
    public ResponseEntity<ListVehiclesResult> listVehicles(@Valid ListVehiclesHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listVehiclesUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update vehicle attributes",
            description = "Updates vehicle specifications such as make, model, seats or accessibility options."
    )
    public ResponseEntity<VehicleResult> updateVehicle(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateVehicleUseCase.execute(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update vehicle status",
            description = "Changes the operational status of a vehicle."
    )
    public ResponseEntity<VehicleResult> updateVehicleStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleStatusHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateVehicleStatusUseCase.execute(id, request));
    }
}
