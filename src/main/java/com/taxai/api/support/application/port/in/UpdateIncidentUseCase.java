package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.IncidentResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.UpdateIncidentHttpRequest;
import java.util.UUID;

public interface UpdateIncidentUseCase { IncidentResult execute(UUID id, UpdateIncidentHttpRequest request); }
