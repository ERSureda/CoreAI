package com.taxai.api.notification.application.port.in;

import com.taxai.api.notification.application.result.ListTemplatesResult;
import com.taxai.api.notification.infrastructure.adapter.in.web.dto.ListTemplatesHttpRequest;

public interface ListTemplatesUseCase { ListTemplatesResult execute(ListTemplatesHttpRequest request); }
