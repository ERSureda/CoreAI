package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.UpdateTariffCommand;
import com.taxai.api.pricing.application.result.TariffResult;

public interface UpdateTariffUseCase {
    TariffResult execute(UpdateTariffCommand command);
}
