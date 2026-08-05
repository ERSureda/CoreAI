package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.AcceptOfferCommand;
import com.taxai.api.dispatch.application.result.OfferResult;

public interface AcceptOfferUseCase {
    OfferResult execute(AcceptOfferCommand command);
}
