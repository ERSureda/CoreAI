package com.taxai.api.notification.application.result;

import java.util.UUID;

public record TemplateResult(
        UUID id,
        String code,
        String channel,
        String language,
        String subject,
        String body,
        Integer version,
        Boolean active
) {}
