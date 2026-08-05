package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.RemovePetCommand;

public interface RemovePetUseCase {
    void execute(RemovePetCommand command);
}
