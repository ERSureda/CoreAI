package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.command.AddPetCommand;
import com.taxai.api.booking.application.result.PetResult;

public interface AddPetUseCase {
    PetResult execute(AddPetCommand command);
}
