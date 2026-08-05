package com.taxai.api.dispatch.infrastructure.adapter.in.web;

import com.taxai.api.dispatch.application.port.in.AcceptOfferViaChannelUseCase;
import com.taxai.api.dispatch.application.port.in.RejectOfferViaChannelUseCase;
import com.taxai.api.dispatch.application.port.in.UpdateDriverLocationUseCase;
import com.taxai.api.dispatch.application.result.DriverLocationResult;
import com.taxai.api.dispatch.application.result.OfferResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.AcceptOfferViaChannelHttpRequest;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.RejectOfferViaChannelHttpRequest;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.UpdateDriverLocationHttpRequest;
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
@RequestMapping("/v1/dispatch/drivers")
@Tag(name = "Driver Channel Communication", description = "API for driver channel events (WebSocket / push responses and location tracking).")
public class DriverChannelController {

    private final DispatchWebMapper mapper;

    private final AcceptOfferViaChannelUseCase acceptOfferViaChannelUseCase;
    private final RejectOfferViaChannelUseCase rejectOfferViaChannelUseCase;
    private final UpdateDriverLocationUseCase updateDriverLocationUseCase;

    @PostMapping("/{driverId}/channel/accept-offer")
    @Operation(
            summary = "Accept offer via channel",
            description = "Processes offer acceptance received via real-time driver channel."
    )
    public ResponseEntity<OfferResult> acceptOfferViaChannel(
            @PathVariable UUID driverId,
            @Valid @RequestBody AcceptOfferViaChannelHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(acceptOfferViaChannelUseCase.execute(driverId, request));
    }

    @PostMapping("/{driverId}/channel/reject-offer")
    @Operation(
            summary = "Reject offer via channel",
            description = "Processes offer rejection received via real-time driver channel."
    )
    public ResponseEntity<OfferResult> rejectOfferViaChannel(
            @PathVariable UUID driverId,
            @Valid @RequestBody RejectOfferViaChannelHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(rejectOfferViaChannelUseCase.execute(driverId, request));
    }

    @PostMapping("/{driverId}/channel/location")
    @Operation(
            summary = "Update driver location",
            description = "Updates driver real-time GPS coordinates via channel."
    )
    public ResponseEntity<DriverLocationResult> updateDriverLocation(
            @PathVariable UUID driverId,
            @Valid @RequestBody UpdateDriverLocationHttpRequest request
    ) {
        return ResponseEntity
                .status(200)
                .body(updateDriverLocationUseCase.execute(driverId, request));
    }
}
