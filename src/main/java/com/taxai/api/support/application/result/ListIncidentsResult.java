package com.taxai.api.support.application.result;

import java.util.List;

public record ListIncidentsResult(
        List<IncidentItem> items,
        String nextCursor
) {}
