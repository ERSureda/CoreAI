package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.CreateTripReceiptCommand;
import com.taxai.api.pricing.application.result.ReceiptResult;

public interface CreateTripReceiptUseCase {
    ReceiptResult execute(CreateTripReceiptCommand command);
}
