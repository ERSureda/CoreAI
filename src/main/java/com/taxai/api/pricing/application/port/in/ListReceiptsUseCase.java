package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.command.ListReceiptsCommand;
import com.taxai.api.pricing.application.result.ListReceiptsResult;

public interface ListReceiptsUseCase {
    ListReceiptsResult execute(ListReceiptsCommand command);
}
