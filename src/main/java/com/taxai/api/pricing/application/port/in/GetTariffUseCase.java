package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.TariffResult;
import java.util.UUID;

public interface GetTariffUseCase { TariffResult execute(UUID id); }
