package com.taxai.api.dispatch.application.result;

import java.util.List;
import java.util.UUID;

public record MatchTripResult(
        UUID tripId,
        Integer wave,
        List<OfferResult> offersCreated
) {}
