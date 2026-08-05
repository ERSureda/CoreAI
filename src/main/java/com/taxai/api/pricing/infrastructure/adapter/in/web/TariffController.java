package com.taxai.api.pricing.infrastructure.adapter.in.web;

import com.taxai.api.pricing.application.port.in.*;
import com.taxai.api.pricing.application.result.ListTariffsResult;
import com.taxai.api.pricing.application.result.TariffResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.*;
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
@RequestMapping("/v1/pricing/tariffs")
@Tag(name = "Tariff Management", description = "API for pricing tariffs, surcharges and rates administration.")
public class TariffController {

    private final PricingWebMapper mapper;

    private final CreateTariffUseCase createTariffUseCase;
    private final GetTariffUseCase getTariffUseCase;
    private final ListTariffsUseCase listTariffsUseCase;
    private final UpdateTariffUseCase updateTariffUseCase;
    private final DeleteTariffUseCase deleteTariffUseCase;

    @PostMapping
    @Operation(
            summary = "Create tariff",
            description = "Configures a new pricing tariff with base fare, rates per km/min and surcharges."
    )
    public ResponseEntity<TariffResult> createTariff(@Valid @RequestBody CreateTariffHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createTariffUseCase.execute(request));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get tariff by ID",
            description = "Retrieves full specifications of a pricing tariff."
    )
    public ResponseEntity<TariffResult> getTariff(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(getTariffUseCase.execute(id));
    }

    @GetMapping
    @Operation(
            summary = "List tariffs",
            description = "Retrieves a paginated list of active tariffs filtered by zone or vehicle type."
    )
    public ResponseEntity<ListTariffsResult> listTariffs(@Valid ListTariffsHttpRequest request) {
        return ResponseEntity
                .status(200)
                .body(listTariffsUseCase.execute(request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Update tariff",
            description = "Partially updates tariff rates or active flag."
    )
    public ResponseEntity<TariffResult> updateTariff(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateTariffHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateTariffUseCase.execute(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete tariff",
            description = "Deletes a pricing tariff configuration."
    )
    public ResponseEntity<Void> deleteTariff(@PathVariable UUID id) {
        deleteTariffUseCase.execute(id);
        return ResponseEntity
                .status(204)
                .build();
    }
}
