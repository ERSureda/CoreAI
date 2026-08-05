package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.*;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface MatchTripUseCase { MatchTripResult execute(UUID tripId); }
