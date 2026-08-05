package com.taxai.api.dispatch.infrastructure.adapter.in.web;

import com.taxai.api.dispatch.application.port.in.*;
import com.taxai.api.dispatch.application.result.ListOffersResult;
import com.taxai.api.dispatch.application.result.OfferResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.ListTripOffersHttpRequest;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.mapper.DispatchWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/dispatch")
@Tag(name = "Dispatch Offer Operations", description = "API for dispatch offer listing and driver responses.")
public class DispatchOfferController {

    private final DispatchWebMapper mapper;

    private final ListTripOffersUseCase listTripOffersUseCase;
    private final ListPendingDriverOffersUseCase listPendingDriverOffersUseCase;
    private final AcceptOfferUseCase acceptOfferUseCase;
    private final RejectOfferUseCase rejectOfferUseCase;

    @GetMapping("/trips/{tripId}/offers")
    @Operation(
            summary = "List trip offers",
            description = "Retrieves all dispatch offers generated for a specific trip."
    )
    public ResponseEntity<ListOffersResult> listTripOffers(
            @PathVariable UUID tripId,
            @Valid ListTripOffersHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(listTripOffersUseCase.execute(tripId, request));
    }

    @GetMapping("/drivers/{driverId}/offers/pending")
    @Operation(
            summary = "List pending driver offers",
            description = "Retrieves currently active pending dispatch offers for a driver."
    )
    public ResponseEntity<ListOffersResult> listPendingDriverOffers(@PathVariable UUID driverId) {
        return ResponseEntity
                .status(200)
                .body(listPendingDriverOffersUseCase.execute(driverId));
    }

    @PostMapping("/offers/{id}/accept")
    @Operation(
            summary = "Accept dispatch offer",
            description = "Accepts a pending dispatch offer."
    )
    public ResponseEntity<OfferResult> acceptOffer(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(acceptOfferUseCase.execute(id));
    }

    @PostMapping("/offers/{id}/reject")
    @Operation(
            summary = "Reject dispatch offer",
            description = "Rejects a pending dispatch offer."
    )
    public ResponseEntity<OfferResult> rejectOffer(@PathVariable UUID id) {
        return ResponseEntity
                .status(200)
                .body(rejectOfferUseCase.execute(id));
    }
}
