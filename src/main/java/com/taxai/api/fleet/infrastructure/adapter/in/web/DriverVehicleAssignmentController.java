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
@Tag(name = "Vehicle Assignment Management", description = "API for driver vehicle assignments.")
public class DriverVehicleAssignmentController {

    private final FleetWebMapper mapper;

    private final CreateVehicleAssignmentUseCase createVehicleAssignmentUseCase;
    private final EndVehicleAssignmentUseCase endVehicleAssignmentUseCase;
    private final GetCurrentVehicleAssignmentUseCase getCurrentVehicleAssignmentUseCase;

    @PostMapping("/drivers/{id}/vehicle-assignments")
    @Operation(
            summary = "Assign vehicle to driver",
            description = "Assigns a fleet vehicle to a specified driver."
    )
    public ResponseEntity<VehicleAssignmentResult> createVehicleAssignment(
            @PathVariable UUID id,
            @Valid @RequestBody CreateVehicleAssignmentHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(createVehicleAssignmentUseCase.execute(id, request));
    }

    @PatchMapping("/vehicle-assignments/{id}/end")
    @Operation(
            summary = "End vehicle assignment",
            description = "Terminates an active vehicle assignment."
    )
    public ResponseEntity<VehicleAssignmentResult> endVehicleAssignment(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(endVehicleAssignmentUseCase.execute(id));
    }

    @GetMapping("/drivers/{id}/vehicle-assignments/current")
    @Operation(
            summary = "Get current vehicle assignment",
            description = "Retrieves the active vehicle assignment for a given driver."
    )
    public ResponseEntity<VehicleAssignmentResult> getCurrentVehicleAssignment(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getCurrentVehicleAssignmentUseCase.execute(id));
    }
}
