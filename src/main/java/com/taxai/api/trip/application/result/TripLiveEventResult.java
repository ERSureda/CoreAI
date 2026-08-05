package com.taxai.api.trip.application.result;

import java.math.BigDecimal;

public record TripLiveEventResult(
        String type,
        String status,
        BigDecimal etaMin,
        Double lat,
        Double lng
) {}
