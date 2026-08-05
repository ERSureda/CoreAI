package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.GetLostItemCommand;
import com.taxai.api.support.application.result.LostItemResult;

public interface GetLostItemUseCase {
    LostItemResult execute(GetLostItemCommand command);
}
