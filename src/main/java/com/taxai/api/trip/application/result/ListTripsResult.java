package com.taxai.api.trip.application.result;

import java.util.List;

public record ListTripsResult(
        List<TripItem> items,
        String nextCursor
) {}
