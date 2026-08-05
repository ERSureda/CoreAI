package com.taxai.api.notification.infrastructure.adapter.in.web;

import com.taxai.api.notification.application.port.in.CreateTemplateUseCase;
import com.taxai.api.notification.application.port.in.ListTemplatesUseCase;
import com.taxai.api.notification.application.port.in.UpdateTemplateUseCase;
import com.taxai.api.notification.application.result.ListTemplatesResult;
import com.taxai.api.notification.application.result.TemplateResult;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.CreateTemplateHttpRequest;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.ListTemplatesHttpRequest;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.UpdateTemplateHttpRequest;
import com.taxai.api.notification.infrastructure.adapter.in.web.mapper.NotificationWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/notifications/templates")
@Tag(name = "Notification Template Management", description = "API for managing notification templates and message channels.")
public class NotificationTemplateController {

    private final NotificationWebMapper mapper;

    private final CreateTemplateUseCase createTemplateUseCase;
    private final ListTemplatesUseCase listTemplatesUseCase;
    private final UpdateTemplateUseCase updateTemplateUseCase;

    @PostMapping
    @Operation(
            summary = "Create template",
            description = "Creates a new notification template for a specific channel and language."
    )
    public ResponseEntity<TemplateResult> createTemplate(@Valid @RequestBody CreateTemplateHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createTemplateUseCase.execute(mapper.toCreateTemplateCommand(request)));
    }

    @GetMapping
    @Operation(
            summary = "List templates",
            description = "Retrieves templates filtered by code, channel or language."
    )
    public ResponseEntity<ListTemplatesResult> listTemplates(@Valid ListTemplatesHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listTemplatesUseCase.execute(mapper.toListTemplatesCommand(request)));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update template",
            description = "Updates body content or active flag of a template."
    )
    public ResponseEntity<TemplateResult> updateTemplate(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTemplateHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateTemplateUseCase.execute(mapper.toUpdateTemplateCommand(id, request)));
    }
}
