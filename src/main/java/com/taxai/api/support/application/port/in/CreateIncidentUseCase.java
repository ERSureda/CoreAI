package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.result.*;
import com.taxai.api.support.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface CreateIncidentUseCase { IncidentResult execute(CreateIncidentHttpRequest request); }
