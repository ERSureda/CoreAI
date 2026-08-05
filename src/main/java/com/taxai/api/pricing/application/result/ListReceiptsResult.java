package com.taxai.api.pricing.application.result;

import java.util.List;

public record ListReceiptsResult(
        List<ReceiptItem> items,
        String nextCursor
) {}
