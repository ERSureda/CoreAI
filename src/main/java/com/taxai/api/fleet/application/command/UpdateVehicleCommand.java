package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record UpdateVehicleCommand(
        UUID id,
        String make,
        String model,
        Integer passengerSeats,
        Boolean wheelchairAccessible,
        Boolean allowsPets
) {
    public UpdateVehicleCommand {
        CommandValidator.start()
                .rejectIfNull(id, "ID_REQUIRED", "ID is required.")
                .validate(UpdateVehicleCommand.class.getSimpleName());
    }
}
