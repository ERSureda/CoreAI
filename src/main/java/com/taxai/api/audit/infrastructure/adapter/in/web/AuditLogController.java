package com.taxai.api.audit.infrastructure.adapter.in.web;

import com.taxai.api.audit.application.port.in.GetAuditLogUseCase;
import com.taxai.api.audit.application.port.in.ListAuditLogsUseCase;
import com.taxai.api.audit.application.result.AuditLogResult;
import com.taxai.api.audit.application.result.ListAuditLogsResult;
import com.taxai.api.audit.infrastructure.adapter.in.web.dto.ListAuditLogsHttpRequest;
import com.taxai.api.audit.infrastructure.adapter.in.web.mapper.AuditWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/audit/logs")
@Tag(name = "Audit Log Management", description = "API for querying system audit trail logs.")
public class AuditLogController {

    private final AuditWebMapper mapper;

    private final ListAuditLogsUseCase listAuditLogsUseCase;
    private final GetAuditLogUseCase getAuditLogUseCase;

    @GetMapping
    @Operation(
            summary = "List audit logs",
            description = "Retrieves a paginated list of audit trail logs filtered by aggregate, actor or timeframe."
    )
    public ResponseEntity<ListAuditLogsResult> listAuditLogs(@Valid ListAuditLogsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listAuditLogsUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get audit log by ID",
            description = "Retrieves a specific audit log entry by its unique identifier."
    )
    public ResponseEntity<AuditLogResult> getAuditLog(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getAuditLogUseCase.execute(id));
    }
}
