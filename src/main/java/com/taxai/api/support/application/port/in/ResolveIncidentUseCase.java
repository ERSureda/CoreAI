package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.IncidentResult;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.ResolveIncidentHttpRequest;
import java.util.UUID;

public interface ResolveIncidentUseCase { IncidentResult execute(UUID id, ResolveIncidentHttpRequest request); }
