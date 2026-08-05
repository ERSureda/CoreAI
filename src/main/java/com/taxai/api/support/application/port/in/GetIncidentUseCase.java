package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.IncidentResult;
import java.util.UUID;

public interface GetIncidentUseCase { IncidentResult execute(UUID id); }
