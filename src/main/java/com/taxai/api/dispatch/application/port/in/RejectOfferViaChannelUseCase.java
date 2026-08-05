package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.RejectOfferViaChannelCommand;
import com.taxai.api.dispatch.application.result.OfferResult;

public interface RejectOfferViaChannelUseCase {
    OfferResult execute(RejectOfferViaChannelCommand command);
}
