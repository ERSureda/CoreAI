package com.taxai.api.trip.application.result;

import java.util.List;

public record TripStatusHistoryResult(
        List<StatusHistoryItem> items
) {}
