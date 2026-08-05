package com.taxai.api.fleet.infrastructure.adapter.in.web;

import com.taxai.api.fleet.application.port.in.CreateVehicleAssignmentUseCase;
import com.taxai.api.fleet.application.port.in.EndVehicleAssignmentUseCase;
import com.taxai.api.fleet.application.port.in.GetCurrentVehicleAssignmentUseCase;
import com.taxai.api.fleet.application.result.VehicleAssignmentResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateVehicleAssignmentHttpRequest;
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
@RequestMapping("/v1/fleet")
@Tag(name = "Driver Vehicle Assignment", description = "API for managing active shift assignments between drivers and vehicles.")
public class DriverVehicleAssignmentController {

    private final FleetWebMapper mapper;

    private final CreateVehicleAssignmentUseCase createVehicleAssignmentUseCase;
    private final EndVehicleAssignmentUseCase endVehicleAssignmentUseCase;
    private final GetCurrentVehicleAssignmentUseCase getCurrentVehicleAssignmentUseCase;

    @PostMapping("/drivers/{driverId}/assignments")
    @Operation(
            summary = "Assign vehicle to driver",
            description = "Creates a new shift vehicle assignment for a driver."
    )
    public ResponseEntity<VehicleAssignmentResult> createVehicleAssignment(
            @PathVariable UUID driverId,
            @Valid @RequestBody CreateVehicleAssignmentHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(createVehicleAssignmentUseCase.execute(mapper.toCreateVehicleAssignmentCommand(driverId, request)));
    }

    @PostMapping("/assignments/{id}/end")
    @Operation(
            summary = "End vehicle assignment",
            description = "Ends an active vehicle assignment shift for a driver."
    )
    public ResponseEntity<VehicleAssignmentResult> endVehicleAssignment(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(endVehicleAssignmentUseCase.execute(mapper.toEndVehicleAssignmentCommand(id)));
    }

    @GetMapping("/drivers/{driverId}/assignments/current")
    @Operation(
            summary = "Get current vehicle assignment",
            description = "Retrieves active vehicle assignment for a given driver."
    )
    public ResponseEntity<VehicleAssignmentResult> getCurrentVehicleAssignment(@PathVariable UUID driverId) {
        return ResponseEntity
                .status(200)
                .body(getCurrentVehicleAssignmentUseCase.execute(mapper.toGetCurrentVehicleAssignmentCommand(driverId)));
    }
}
