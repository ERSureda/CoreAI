package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.CreateTariffCommand;
import com.taxai.api.pricing.application.result.TariffResult;

public interface CreateTariffUseCase {
    TariffResult execute(CreateTariffCommand command);
}
