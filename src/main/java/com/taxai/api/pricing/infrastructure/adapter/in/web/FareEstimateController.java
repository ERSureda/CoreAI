package com.taxai.api.pricing.infrastructure.adapter.in.web;

import com.taxai.api.pricing.application.port.in.CreateFareEstimateUseCase;
import com.taxai.api.pricing.application.port.in.ListBookingFareEstimatesUseCase;
import com.taxai.api.pricing.application.result.FareEstimateResult;
import com.taxai.api.pricing.application.result.ListFareEstimatesResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.CreateFareEstimateHttpRequest;
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
@Tag(name = "Fare Estimate Management", description = "API for trip fare calculation and estimation.")
public class FareEstimateController {

    private final PricingWebMapper mapper;

    private final CreateFareEstimateUseCase createFareEstimateUseCase;
    private final ListBookingFareEstimatesUseCase listBookingFareEstimatesUseCase;

    @PostMapping("/estimates")
    @Operation(
            summary = "Create fare estimate",
            description = "Calculates estimated fare range based on origin, destination and vehicle type."
    )
    public ResponseEntity<FareEstimateResult> createFareEstimate(@Valid @RequestBody CreateFareEstimateHttpRequest request) {
        return ResponseEntity
                .status(201)
                .body(createFareEstimateUseCase.execute(mapper.toCreateFareEstimateCommand(request)));
    }

    @GetMapping("/bookings/{bookingId}/estimates")
    @Operation(
            summary = "List booking fare estimates",
            description = "Retrieves all fare estimates calculated for a specific booking."
    )
    public ResponseEntity<ListFareEstimatesResult> listBookingFareEstimates(@PathVariable UUID bookingId) {
        return ResponseEntity
                .status(200)
                .body(listBookingFareEstimatesUseCase.execute(mapper.toListBookingFareEstimatesCommand(bookingId)));
    }
}
