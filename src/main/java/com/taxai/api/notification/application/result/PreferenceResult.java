package com.taxai.api.notification.application.result;

import java.util.UUID;

public record PreferenceResult(
        String ownerType,
        UUID ownerId,
        String channel,
        Boolean enabled,
        String quietFrom,
        String quietTo,
        String timezone
) {}
