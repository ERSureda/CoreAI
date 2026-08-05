package com.taxai.api.notification.application.result;

import java.util.List;

public record ListPreferencesResult(
        List<PreferenceResult> items
) {}
