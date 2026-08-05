package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.RemoveLuggageCommand;

public interface RemoveLuggageUseCase {
    void execute(RemoveLuggageCommand command);
}
