package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.AddLuggageCommand;
import com.taxai.api.booking.application.result.LuggageResult;

public interface AddLuggageUseCase {
    LuggageResult execute(AddLuggageCommand command);
}
