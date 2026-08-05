package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.AcceptOfferViaChannelCommand;
import com.taxai.api.dispatch.application.result.OfferResult;

public interface AcceptOfferViaChannelUseCase {
    OfferResult execute(AcceptOfferViaChannelCommand command);
}
