package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.ListLostItemsCommand;
import com.taxai.api.support.application.result.ListLostItemsResult;

public interface ListLostItemsUseCase {
    ListLostItemsResult execute(ListLostItemsCommand command);
}
