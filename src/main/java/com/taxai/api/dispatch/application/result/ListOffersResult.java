package com.taxai.api.dispatch.application.result;

import java.util.List;

public record ListOffersResult(
        List<OfferResult> items,
        String nextCursor
) {}
