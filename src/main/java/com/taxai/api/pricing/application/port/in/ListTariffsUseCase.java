package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.ListTariffsCommand;
import com.taxai.api.pricing.application.result.ListTariffsResult;

public interface ListTariffsUseCase {
    ListTariffsResult execute(ListTariffsCommand command);
}
