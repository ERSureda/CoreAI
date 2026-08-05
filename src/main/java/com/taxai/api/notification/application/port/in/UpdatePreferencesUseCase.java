package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.result.PreferenceResult;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.UpdatePreferencesHttpRequest;

public interface UpdatePreferencesUseCase { PreferenceResult execute(UpdatePreferencesHttpRequest request); }
