package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.GetTariffCommand;
import com.taxai.api.pricing.application.result.TariffResult;

public interface GetTariffUseCase {
    TariffResult execute(GetTariffCommand command);
}
