package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.command.UpdatePreferencesCommand;
import com.taxai.api.notification.application.result.PreferenceResult;

public interface UpdatePreferencesUseCase {
    PreferenceResult execute(UpdatePreferencesCommand command);
}
