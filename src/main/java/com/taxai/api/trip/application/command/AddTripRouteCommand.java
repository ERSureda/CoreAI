package com.taxai.api.trip.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record AddTripRouteCommand(
        UUID tripId,
        String reason,
        String provider,
        String encodedPolyline,
        Integer distanceM,
        Integer durationS,
        Integer durationTrafficS,
        String waypoints
) {
    public AddTripRouteCommand {
        CommandValidator.start()
                .rejectIfNull(tripId, "TRIP_ID_REQUIRED", "Trip ID is required.")
                .rejectIfBlank(reason, "REASON_REQUIRED", "Reason is required.")
                .rejectIfBlank(provider, "PROVIDER_REQUIRED", "Provider is required.")
                .rejectIfBlank(encodedPolyline, "ENCODED_POLYLINE_REQUIRED", "Encoded polyline is required.")
                .rejectIfNull(distanceM, "DISTANCE_M_REQUIRED", "Distance M is required.")
                .rejectIfNull(durationS, "DURATION_S_REQUIRED", "Duration S is required.")
                .validate(AddTripRouteCommand.class.getSimpleName());
    }
}
