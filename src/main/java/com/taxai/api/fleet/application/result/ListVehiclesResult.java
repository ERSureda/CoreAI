package com.taxai.api.fleet.application.result;

import java.util.List;

public record ListVehiclesResult(
        List<VehicleResult> items,
        String nextCursor
) {}
