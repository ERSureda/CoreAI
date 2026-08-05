package com.taxai.api.support.infrastructure.adapter.in.web;

import com.taxai.api.support.application.port.in.*;
import com.taxai.api.support.application.result.IncidentResult;
import com.taxai.api.support.application.result.ListIncidentsResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.*;
import com.taxai.api.support.infrastructure.adapter.in.web.mapper.SupportWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/support/incidents")
@Tag(name = "Support Incident Management", description = "API for reporting, tracking and resolving customer and driver support incidents.")
public class IncidentController {

    private final SupportWebMapper mapper;

    private final CreateIncidentUseCase createIncidentUseCase;
    private final GetIncidentUseCase getIncidentUseCase;
    private final ListIncidentsUseCase listIncidentsUseCase;
    private final UpdateIncidentUseCase updateIncidentUseCase;
    private final AssignIncidentUseCase assignIncidentUseCase;
    private final ResolveIncidentUseCase resolveIncidentUseCase;
    private final CloseIncidentUseCase closeIncidentUseCase;

    @PostMapping
    @Operation(
            summary = "Report support incident",
            description = "Creates a new support incident report linked to a trip or user."
    )
    public ResponseEntity<IncidentResult> createIncident(@Valid @RequestBody CreateIncidentHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createIncidentUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get incident by ID",
            description = "Retrieves full details and status of a support incident."
    )
    public ResponseEntity<IncidentResult> getIncident(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getIncidentUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List support incidents",
            description = "Retrieves a paginated list of support incidents filtered by status, priority or trip."
    )
    public ResponseEntity<ListIncidentsResult> listIncidents(@Valid ListIncidentsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listIncidentsUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update incident",
            description = "Updates description or priority of a support incident."
    )
    public ResponseEntity<IncidentResult> updateIncident(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateIncidentHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateIncidentUseCase.execute(id, request));
    }

    @PostMapping("/{id}/assign")
    @Operation(
            summary = "Assign incident agent",
            description = "Assigns a support agent to handle the incident."
    )
    public ResponseEntity<IncidentResult> assignIncident(
            @PathVariable UUID id,
            @Valid @RequestBody AssignIncidentHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(assignIncidentUseCase.execute(id, request));
    }

    @PostMapping("/{id}/resolve")
    @Operation(
            summary = "Resolve incident",
            description = "Marks an incident as resolved with resolution details."
    )
    public ResponseEntity<IncidentResult> resolveIncident(
            @PathVariable UUID id,
            @Valid @RequestBody ResolveIncidentHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(resolveIncidentUseCase.execute(id, request));
    }

    @PostMapping("/{id}/close")
    @Operation(
            summary = "Close incident",
            description = "Closes a resolved support incident."
    )
    public ResponseEntity<IncidentResult> closeIncident(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(closeIncidentUseCase.execute(id));
    }
}
