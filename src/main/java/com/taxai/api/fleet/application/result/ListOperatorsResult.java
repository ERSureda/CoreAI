package com.taxai.api.fleet.application.result;

import java.util.List;

public record ListOperatorsResult(
        List<OperatorResult> items,
        String nextCursor
) {}
