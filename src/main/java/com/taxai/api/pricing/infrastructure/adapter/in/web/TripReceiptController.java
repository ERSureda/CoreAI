package com.taxai.api.pricing.infrastructure.adapter.in.web;

import com.taxai.api.pricing.application.port.in.CreateTripReceiptUseCase;
import com.taxai.api.pricing.application.port.in.GetTripReceiptUseCase;
import com.taxai.api.pricing.application.port.in.ListReceiptsUseCase;
import com.taxai.api.pricing.application.result.ListReceiptsResult;
import com.taxai.api.pricing.application.result.ReceiptResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.CreateTripReceiptHttpRequest;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.ListReceiptsHttpRequest;
import com.taxai.api.pricing.infrastructure.adapter.in.web.mapper.PricingWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/pricing")
@Tag(name = "Trip Receipt Management", description = "API for trip receipts generation and payment recording.")
public class TripReceiptController {

    private final PricingWebMapper mapper;

    private final CreateTripReceiptUseCase createTripReceiptUseCase;
    private final GetTripReceiptUseCase getTripReceiptUseCase;
    private final ListReceiptsUseCase listReceiptsUseCase;

    @PostMapping("/trips/{id}/receipt")
    @Operation(
            summary = "Create trip receipt",
            description = "Generates and records payment receipt for a completed trip."
    )
    public ResponseEntity<ReceiptResult> createTripReceipt(
            @PathVariable UUID id,
            @Valid @RequestBody CreateTripReceiptHttpRequest request
    ) {
        return ResponseEntity
                .status(201)
                .body(createTripReceiptUseCase.execute(id, request));
    }

    @GetMapping("/trips/{id}/receipt")
    @Operation(
            summary = "Get trip receipt",
            description = "Retrieves stored payment receipt details for a specific trip."
    )
    public ResponseEntity<ReceiptResult> getTripReceipt(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getTripReceiptUseCase.execute(id));
    }

    @GetMapping("/receipts")
    @Operation(
            summary = "List receipts",
            description = "Retrieves a paginated list of receipts for a tenant within a date range."
    )
    public ResponseEntity<ListReceiptsResult> listReceipts(@Valid ListReceiptsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listReceiptsUseCase.execute(request));
    }
}
