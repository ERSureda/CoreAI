package com.taxai.api.notification.application.result;

import java.util.List;

public record ListTemplatesResult(
        List<TemplateResult> items
) {}
