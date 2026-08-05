package com.taxai.api.support.application.result;

import java.util.List;

public record ListLostItemsResult(
        List<LostItemItem> items,
        String nextCursor
) {}
