package com.taxai.api.dispatch.application.result;

import java.time.Instant;
import java.util.UUID;

public record DriverLocationResult(
        UUID driverId,
        Instant receivedAt
) {}
