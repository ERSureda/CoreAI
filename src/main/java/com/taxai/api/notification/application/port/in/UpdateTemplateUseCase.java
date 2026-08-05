package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.result.TemplateResult;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.UpdateTemplateHttpRequest;
import java.util.UUID;

public interface UpdateTemplateUseCase { TemplateResult execute(UUID id, UpdateTemplateHttpRequest request); }
