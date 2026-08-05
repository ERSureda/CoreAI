package com.taxai.api.fleet.infrastructure.adapter.in.web;

import com.taxai.api.fleet.application.port.in.UpdateDriverPresenceUseCase;
import com.taxai.api.fleet.application.result.DriverPresenceResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateDriverPresenceHttpRequest;
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
@Tag(name = "Driver Presence", description = "API for driver shift presence and availability state transitions.")
public class DriverPresenceController {

    private final FleetWebMapper mapper;

    private final UpdateDriverPresenceUseCase updateDriverPresenceUseCase;

    @PostMapping("/{driverId}/presence")
    @Operation(
            summary = "Update driver presence",
            description = "Updates driver online status (e.g. online, offline, busy) and active zone."
    )
    public ResponseEntity<DriverPresenceResult> updateDriverPresence(
            @PathVariable UUID driverId,
            @Valid @RequestBody UpdateDriverPresenceHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateDriverPresenceUseCase.execute(mapper.toUpdateDriverPresenceCommand(driverId, request)));
    }
}
