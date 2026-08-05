package com.taxai.api.support.application.port.in;

import com.taxai.api.support.application.command.UpdateLostItemStatusCommand;
import com.taxai.api.support.application.result.LostItemResult;

public interface UpdateLostItemStatusUseCase {
    LostItemResult execute(UpdateLostItemStatusCommand command);
}
