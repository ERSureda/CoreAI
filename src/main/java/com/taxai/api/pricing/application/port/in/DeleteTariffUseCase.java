package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.DeleteTariffCommand;

public interface DeleteTariffUseCase {
    void execute(DeleteTariffCommand command);
}
