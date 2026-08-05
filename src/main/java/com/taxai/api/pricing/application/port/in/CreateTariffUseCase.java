package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.TariffResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.CreateTariffHttpRequest;

public interface CreateTariffUseCase { TariffResult execute(CreateTariffHttpRequest request); }
