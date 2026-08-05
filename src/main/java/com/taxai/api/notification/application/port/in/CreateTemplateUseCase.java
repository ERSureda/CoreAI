package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.result.*;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface CreateTemplateUseCase { TemplateResult execute(CreateTemplateHttpRequest request); }
