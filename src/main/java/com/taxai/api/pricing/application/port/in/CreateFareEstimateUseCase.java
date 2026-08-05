package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.*;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface CreateFareEstimateUseCase { FareEstimateResult execute(CreateFareEstimateHttpRequest request); }
