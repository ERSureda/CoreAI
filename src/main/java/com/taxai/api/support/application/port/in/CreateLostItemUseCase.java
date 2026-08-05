package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.CreateLostItemCommand;
import com.taxai.api.support.application.result.LostItemResult;

public interface CreateLostItemUseCase {
    LostItemResult execute(CreateLostItemCommand command);
}
