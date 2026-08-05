package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.command.UpdateTemplateCommand;
import com.taxai.api.notification.application.result.TemplateResult;

public interface UpdateTemplateUseCase {
    TemplateResult execute(UpdateTemplateCommand command);
}
