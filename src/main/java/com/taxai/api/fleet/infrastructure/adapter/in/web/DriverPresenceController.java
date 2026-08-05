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
@Tag(name = "Driver Presence Management", description = "API for tracking driver availability and zone presence.")
public class DriverPresenceController {

    private final FleetWebMapper mapper;

    private final UpdateDriverPresenceUseCase updateDriverPresenceUseCase;

    @PostMapping("/{id}/presence")
    @Operation(
            summary = "Update driver presence",
            description = "Updates the driver's current availability status and optional zone location."
    )
    public ResponseEntity<DriverPresenceResult> updateDriverPresence(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateDriverPresenceHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateDriverPresenceUseCase.execute(id, request));
    }
}
