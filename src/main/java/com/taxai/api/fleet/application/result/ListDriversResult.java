package com.taxai.api.fleet.application.result;

import java.util.List;

public record ListDriversResult(
        List<DriverResult> items,
        String nextCursor
) {}
