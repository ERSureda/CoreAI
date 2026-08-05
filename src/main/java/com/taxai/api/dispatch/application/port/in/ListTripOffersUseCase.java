package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.command.ListTripOffersCommand;
import com.taxai.api.dispatch.application.result.ListOffersResult;

public interface ListTripOffersUseCase {
    ListOffersResult execute(ListTripOffersCommand command);
}
