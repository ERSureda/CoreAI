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
@Tag(name = "Vehicle Management", description = "API for managing fleet vehicles and operational statuses.")
public class VehicleController {

    private final FleetWebMapper mapper;

    private final CreateVehicleUseCase createVehicleUseCase;
    private final GetVehicleUseCase getVehicleUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final UpdateVehicleStatusUseCase updateVehicleStatusUseCase;

    @PostMapping("/tenants/{tenantId}")
    @Operation(
            summary = "Create vehicle",
            description = "Registers a new vehicle under a tenant."
    )
    public ResponseEntity<VehicleResult> createVehicle(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CreateVehicleHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(createVehicleUseCase.execute(mapper.toCreateVehicleCommand(tenantId, request)));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get vehicle by ID",
            description = "Retrieves vehicle details by ID."
    )
    public ResponseEntity<VehicleResult> getVehicle(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getVehicleUseCase.execute(mapper.toGetVehicleCommand(id)));
    }

    @GetMapping
    @Operation(
            summary = "List vehicles",
            description = "Retrieves a paginated list of vehicles for a tenant."
    )
    public ResponseEntity<ListVehiclesResult> listVehicles(@Valid ListVehiclesHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listVehiclesUseCase.execute(mapper.toListVehiclesCommand(request)));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update vehicle",
            description = "Updates vehicle attributes such as model or passenger capacity."
    )
    public ResponseEntity<VehicleResult> updateVehicle(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateVehicleUseCase.execute(mapper.toUpdateVehicleCommand(id, request)));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update vehicle status",
            description = "Updates operational status of a vehicle (e.g., active, maintenance)."
    )
    public ResponseEntity<VehicleResult> updateVehicleStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleStatusHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateVehicleStatusUseCase.execute(mapper.toUpdateVehicleStatusCommand(id, request)));
    }
}
