package com.taxai.api.fleet.infrastructure.adapter.in.web;

import com.taxai.api.fleet.application.port.in.CreateTenantUseCase;
import com.taxai.api.fleet.application.port.in.GetTenantUseCase;
import com.taxai.api.fleet.application.port.in.UpdateTenantUseCase;
import com.taxai.api.fleet.application.result.TenantResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateTenantHttpRequest;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateTenantHttpRequest;
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
@RequestMapping("/v1/fleet/tenants")
@Tag(name = "Tenant Management", description = "API for managing multi-tenant fleet organization accounts.")
public class TenantController {

    private final FleetWebMapper mapper;

    private final CreateTenantUseCase createTenantUseCase;
    private final GetTenantUseCase getTenantUseCase;
    private final UpdateTenantUseCase updateTenantUseCase;

    @PostMapping
    @Operation(
            summary = "Create tenant",
            description = "Creates a new multi-tenant organization account."
    )
    public ResponseEntity<TenantResult> createTenant(@Valid @RequestBody CreateTenantHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createTenantUseCase.execute(mapper.toCreateTenantCommand(request)));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get tenant by ID",
            description = "Retrieves tenant organization details by ID."
    )
    public ResponseEntity<TenantResult> getTenant(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getTenantUseCase.execute(mapper.toGetTenantCommand(id)));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update tenant",
            description = "Partially updates tenant configuration or name."
    )
    public ResponseEntity<TenantResult> updateTenant(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTenantHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateTenantUseCase.execute(mapper.toUpdateTenantCommand(id, request)));
    }
}
