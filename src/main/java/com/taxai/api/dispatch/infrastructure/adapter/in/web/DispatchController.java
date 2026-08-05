package com.taxai.api.dispatch.infrastructure.adapter.in.web;

import com.taxai.api.dispatch.application.port.in.AssignNearestDriverUseCase;
import com.taxai.api.dispatch.application.port.in.MatchTripUseCase;
import com.taxai.api.dispatch.application.result.MatchTripResult;
import com.taxai.api.dispatch.application.result.OfferResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.AssignNearestDriverHttpRequest;
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
@RequestMapping("/v1/dispatch/trips")
@Tag(name = "Dispatch Trip Operations", description = "API for trip matching waves and nearest driver assignment.")
public class DispatchController {

    private final DispatchWebMapper mapper;

    private final MatchTripUseCase matchTripUseCase;
    private final AssignNearestDriverUseCase assignNearestDriverUseCase;

    @PostMapping("/{tripId}/match")
    @Operation(
            summary = "Trigger trip matching",
            description = "Triggers a dispatch matching wave to find candidate drivers for a trip."
    )
    public ResponseEntity<MatchTripResult> matchTrip(@PathVariable UUID tripId) {
        return ResponseEntity
                .status(200)
                .body(matchTripUseCase.execute(tripId));
    }

    @PostMapping("/{tripId}/assign-nearest")
    @Operation(
            summary = "Assign nearest driver",
            description = "Dispatches an offer to the nearest available driver within optional zone constraints."
    )
    public ResponseEntity<OfferResult> assignNearestDriver(
            @PathVariable UUID tripId,
            @RequestBody(required = false) AssignNearestDriverHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(assignNearestDriverUseCase.execute(tripId, request));
    }
}
