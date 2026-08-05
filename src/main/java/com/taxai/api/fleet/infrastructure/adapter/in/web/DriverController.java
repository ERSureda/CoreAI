package com.taxai.api.fleet.infrastructure.adapter.in.web;

import com.taxai.api.fleet.application.port.in.*;
import com.taxai.api.fleet.application.result.DriverResult;
import com.taxai.api.fleet.application.result.ListDriversResult;
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
@RequestMapping("/v1/fleet/drivers")
@Tag(name = "Driver Management", description = "API for driver registration and administration.")
public class DriverController {

    private final FleetWebMapper mapper;

    private final CreateDriverUseCase createDriverUseCase;
    private final GetDriverUseCase getDriverUseCase;
    private final ListDriversUseCase listDriversUseCase;
    private final UpdateDriverUseCase updateDriverUseCase;
    private final UpdateDriverStatusUseCase updateDriverStatusUseCase;

    @PostMapping
    @Operation(
            summary = "Register driver",
            description = "Registers a new driver under a tenant with employee details."
    )
    public ResponseEntity<DriverResult> createDriver(@Valid @RequestBody CreateDriverHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createDriverUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get driver by ID",
            description = "Retrieves profile and operational details of a driver."
    )
    public ResponseEntity<DriverResult> getDriver(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getDriverUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List drivers",
            description = "Retrieves a paginated list of drivers belonging to a tenant."
    )
    public ResponseEntity<ListDriversResult> listDrivers(@Valid ListDriversHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listDriversUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update driver details",
            description = "Updates personal details such as full name or contact phone."
    )
    public ResponseEntity<DriverResult> updateDriver(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDriverHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateDriverUseCase.execute(id, request));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update driver administrative status",
            description = "Updates administrative status of a driver (e.g. active, suspended)."
    )
    public ResponseEntity<DriverResult> updateDriverStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDriverStatusHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateDriverStatusUseCase.execute(id, request));
    }
}
