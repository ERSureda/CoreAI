package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.command.CreateTemplateCommand;
import com.taxai.api.notification.application.result.TemplateResult;

public interface CreateTemplateUseCase {
    TemplateResult execute(CreateTemplateCommand command);
}
