package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.result.ListPreferencesResult;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.GetPreferencesHttpRequest;

public interface GetPreferencesUseCase { ListPreferencesResult execute(GetPreferencesHttpRequest request); }
