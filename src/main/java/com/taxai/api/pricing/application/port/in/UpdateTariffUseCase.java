package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.TariffResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.UpdateTariffHttpRequest;
import java.util.UUID;

public interface UpdateTariffUseCase { TariffResult execute(UUID id, UpdateTariffHttpRequest request); }
