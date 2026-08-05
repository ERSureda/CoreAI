package com.taxai.api.booking.infrastructure.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CreateBookingHttpRequest(
        @Schema(description = "Tenant identifier.", example = "a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d")
        @NotNull(message = "TENANT-ID_REQUIRED")
        UUID tenantId,

        @Schema(description = "Booking channel.", example = "WHATSAPP")
        @NotBlank(message = "CHANNEL_REQUIRED")
        String channel,

        @Schema(description = "Booking type category.", example = "IMMEDIATE")
        @NotBlank(message = "TYPE_REQUIRED")
        String type,

        @Schema(description = "Passenger phone number.", example = "+34612345678")
        @NotBlank(message = "PASSENGER-PHONE_REQUIRED")
        String passengerPhone,

        @Schema(description = "Optional scheduled pickup time in UTC.", example = "2026-08-06T15:00:00Z")
        Instant scheduledPickupAt,

        @Schema(description = "Optional recurrence rule specification (RRULE).", example = "FREQ=WEEKLY;BYDAY=MO,WE,FR")
        String recurrenceRule,

        @Schema(description = "Number of passengers.", example = "2")
        @NotNull(message = "PASSENGER-COUNT_REQUIRED")
        @Min(value = 1, message = "PASSENGER-COUNT_INVALID")
        Integer passengerCount,

        @Schema(description = "Optional required vehicle type.", example = "SEDAN")
        String vehicleTypeRequired,

        @Schema(description = "Optional child seat requirement flag.", example = "false")
        Boolean needsChildSeat,

        @Schema(description = "Optional wheelchair access requirement flag.", example = "false")
        Boolean wheelchairRequired,

        @Schema(description = "Optional additional booking instructions.", example = "Pickup at terminal 1 arrivals")
        String notes,

        @Schema(description = "Optional source AI conversation ID.", example = "49228997-624e-4ff8-9821-89aff50cb863")
        UUID sourceConversationId,

        @Schema(description = "Idempotency key for request deduplication.", example = "7d8f9a0b-1c2d-3e4f-5a6b-7c8d9e0f1a2b")
        @NotBlank(message = "IDEMPOTENCY-KEY_REQUIRED")
        String idempotencyKey,

        @Schema(description = "Optional list of luggage items.")
        List<AddLuggageHttpRequest> luggage,

        @Schema(description = "Optional list of pet items.")
        List<AddPetHttpRequest> pets
) {}
