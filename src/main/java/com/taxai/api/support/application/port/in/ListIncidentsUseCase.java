package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.ListIncidentsResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.ListIncidentsHttpRequest;

public interface ListIncidentsUseCase { ListIncidentsResult execute(ListIncidentsHttpRequest request); }
