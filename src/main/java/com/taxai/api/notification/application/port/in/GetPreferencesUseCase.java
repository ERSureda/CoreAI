package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.command.GetPreferencesCommand;
import com.taxai.api.notification.application.result.ListPreferencesResult;

public interface GetPreferencesUseCase {
    ListPreferencesResult execute(GetPreferencesCommand command);
}
