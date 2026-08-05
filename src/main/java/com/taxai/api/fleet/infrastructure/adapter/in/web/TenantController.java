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
@Tag(name = "Tenant Management", description = "API for tenant management and configuration.")
public class TenantController {

    private final FleetWebMapper mapper;

    private final CreateTenantUseCase createTenantUseCase;
    private final GetTenantUseCase getTenantUseCase;
    private final UpdateTenantUseCase updateTenantUseCase;

    @PostMapping
    @Operation(
            summary = "Create a new tenant",
            description = "Registers a new tenant enterprise in the system with initial region and language configuration."
    )
    public ResponseEntity<TenantResult> createTenant(@Valid @RequestBody CreateTenantHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createTenantUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get tenant by ID",
            description = "Retrieves current details and operational status of a specific tenant by its unique identifier."
    )
    public ResponseEntity<TenantResult> getTenant(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getTenantUseCase.execute(id));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update tenant details",
            description = "Partially updates an existing tenant name or active operational status."
    )
    public ResponseEntity<TenantResult> updateTenant(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTenantHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateTenantUseCase.execute(id, request));
    }
}
