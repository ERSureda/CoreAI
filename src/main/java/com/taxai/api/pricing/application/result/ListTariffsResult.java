package com.taxai.api.pricing.application.result;

import java.util.List;

public record ListTariffsResult(
        List<TariffResult> items,
        String nextCursor
) {}
