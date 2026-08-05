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
@Tag(name = "Operator Management", description = "API for fleet operator management.")
public class OperatorController {

    private final FleetWebMapper mapper;

    private final CreateOperatorUseCase createOperatorUseCase;
    private final GetOperatorUseCase getOperatorUseCase;
    private final ListOperatorsUseCase listOperatorsUseCase;
    private final UpdateOperatorUseCase updateOperatorUseCase;

    @PostMapping
    @Operation(
            summary = "Create fleet operator",
            description = "Creates a new fleet operator user assigned to a specific tenant."
    )
    public ResponseEntity<OperatorResult> createOperator(@Valid @RequestBody CreateOperatorHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createOperatorUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get operator by ID",
            description = "Retrieves the operator details by unique identifier."
    )
    public ResponseEntity<OperatorResult> getOperator(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getOperatorUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List tenant operators",
            description = "Retrieves a paginated list of operators associated with a tenant."
    )
    public ResponseEntity<ListOperatorsResult> listOperators(@Valid ListOperatorsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listOperatorsUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update operator details",
            description = "Partially updates an operator's role or status."
    )
    public ResponseEntity<OperatorResult> updateOperator(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateOperatorHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateOperatorUseCase.execute(id, request));
    }
}