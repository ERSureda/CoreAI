package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.RejectOfferCommand;
import com.taxai.api.dispatch.application.result.OfferResult;

public interface RejectOfferUseCase {
    OfferResult execute(RejectOfferCommand command);
}
