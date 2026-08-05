package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.command.ListTemplatesCommand;
import com.taxai.api.notification.application.result.ListTemplatesResult;

public interface ListTemplatesUseCase {
    ListTemplatesResult execute(ListTemplatesCommand command);
}
