package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.GetTripReceiptCommand;
import com.taxai.api.pricing.application.result.ReceiptResult;

public interface GetTripReceiptUseCase {
    ReceiptResult execute(GetTripReceiptCommand command);
}
