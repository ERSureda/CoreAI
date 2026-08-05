package com.taxai.api.support.infrastructure.adapter.in.web;

import com.taxai.api.support.application.port.in.CreateLostItemUseCase;
import com.taxai.api.support.application.port.in.GetLostItemUseCase;
import com.taxai.api.support.application.port.in.ListLostItemsUseCase;
import com.taxai.api.support.application.port.in.UpdateLostItemStatusUseCase;
import com.taxai.api.support.application.result.ListLostItemsResult;
import com.taxai.api.support.application.result.LostItemResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.CreateLostItemHttpRequest;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.ListLostItemsHttpRequest;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.UpdateLostItemStatusHttpRequest;
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
@RequestMapping("/v1/support/lost-items")
@Tag(name = "Lost Item Management", description = "API for reporting, tracking and returning items lost during trips.")
public class LostItemController {

    private final SupportWebMapper mapper;

    private final CreateLostItemUseCase createLostItemUseCase;
    private final GetLostItemUseCase getLostItemUseCase;
    private final ListLostItemsUseCase listLostItemsUseCase;
    private final UpdateLostItemStatusUseCase updateLostItemStatusUseCase;

    @PostMapping
    @Operation(
            summary = "Report lost item",
            description = "Creates a new lost item claim linked to a trip."
    )
    public ResponseEntity<LostItemResult> createLostItem(@Valid @RequestBody CreateLostItemHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createLostItemUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get lost item by ID",
            description = "Retrieves current status and details of a lost item claim."
    )
    public ResponseEntity<LostItemResult> getLostItem(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getLostItemUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List lost items",
            description = "Retrieves a paginated list of lost item claims filtered by trip or status."
    )
    public ResponseEntity<ListLostItemsResult> listLostItems(@Valid ListLostItemsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listLostItemsUseCase.execute(request));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update lost item status",
            description = "Updates operational tracking status of a lost item (e.g. stored, returned)."
    )
    public ResponseEntity<LostItemResult> updateLostItemStatus(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateLostItemStatusHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateLostItemStatusUseCase.execute(id, request));
    }
}
