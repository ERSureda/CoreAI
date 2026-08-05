package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.ListPendingDriverOffersCommand;
import com.taxai.api.dispatch.application.result.ListOffersResult;

public interface ListPendingDriverOffersUseCase {
    ListOffersResult execute(ListPendingDriverOffersCommand command);
}
