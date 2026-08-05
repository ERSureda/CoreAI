package com.taxai.api.fleet.infrastructure.adapter.in.web;

import com.taxai.api.fleet.application.port.in.CreateOperatorUseCase;
import com.taxai.api.fleet.application.port.in.GetOperatorUseCase;
import com.taxai.api.fleet.application.port.in.ListOperatorsUseCase;
import com.taxai.api.fleet.application.port.in.UpdateOperatorUseCase;
import com.taxai.api.fleet.application.result.ListOperatorsResult;
import com.taxai.api.fleet.application.result.OperatorResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.CreateOperatorHttpRequest;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.ListOperatorsHttpRequest;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.UpdateOperatorHttpRequest;
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
@RequestMapping("/v1/fleet/operators")
@Tag(name = "Operator Management", description = "API for managing fleet operators and dispatch staff.")
public class OperatorController {

    private final FleetWebMapper mapper;

    private final CreateOperatorUseCase createOperatorUseCase;
    private final GetOperatorUseCase getOperatorUseCase;
    private final ListOperatorsUseCase listOperatorsUseCase;
    private final UpdateOperatorUseCase updateOperatorUseCase;

    @PostMapping("/tenants/{tenantId}")
    @Operation(
            summary = "Create operator",
            description = "Registers a new operator staff member linked to a tenant."
    )
    public ResponseEntity<OperatorResult> createOperator(
            @PathVariable UUID tenantId,
            @Valid @RequestBody CreateOperatorHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(createOperatorUseCase.execute(mapper.toCreateOperatorCommand(tenantId, request)));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get operator by ID",
            description = "Retrieves details of a specific operator staff member."
    )
    public ResponseEntity<OperatorResult> getOperator(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getOperatorUseCase.execute(mapper.toGetOperatorCommand(id)));
    }

    @GetMapping
    @Operation(
            summary = "List operators",
            description = "Retrieves a paginated list of operators for a tenant."
    )
    public ResponseEntity<ListOperatorsResult> listOperators(@Valid ListOperatorsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listOperatorsUseCase.execute(mapper.toListOperatorsCommand(request)));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update operator",
            description = "Updates operator role or active status."
    )
    public ResponseEntity<OperatorResult> updateOperator(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOperatorHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateOperatorUseCase.execute(mapper.toUpdateOperatorCommand(id, request)));
    }
}
