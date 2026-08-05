package com.taxai.api.pricing.application.result;

import java.util.List;

public record ListFareEstimatesResult(
        List<FareEstimateResult> items
) {}
