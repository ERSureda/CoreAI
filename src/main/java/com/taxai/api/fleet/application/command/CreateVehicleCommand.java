package com.taxai.api.fleet.application.command;

import com.taxai.api.shared.application.validation.CommandValidator;
import java.util.UUID;

public record CreateVehicleCommand(
        UUID tenantId,
        String plate,
        String make,
        String model,
        String type,
        Integer passengerSeats,
        Boolean wheelchairAccessible,
        Boolean allowsPets
) {
    public CreateVehicleCommand {
        CommandValidator.start()
                .rejectIfNull(tenantId, "TENANT_ID_REQUIRED", "Tenant ID is required.")
                .rejectIfBlank(plate, "PLATE_REQUIRED", "Plate is required.")
                .rejectIfBlank(make, "MAKE_REQUIRED", "Make is required.")
                .rejectIfBlank(model, "MODEL_REQUIRED", "Model is required.")
                .rejectIfBlank(type, "TYPE_REQUIRED", "Type is required.")
                .rejectIfNull(passengerSeats, "PASSENGER_SEATS_REQUIRED", "Passenger seats is required.")
                .rejectIfNull(wheelchairAccessible, "WHEELCHAIR_ACCESSIBLE_REQUIRED", "Wheelchair accessible is required.")
                .rejectIfNull(allowsPets, "ALLOWS_PETS_REQUIRED", "Allows pets is required.")
                .validate(CreateVehicleCommand.class.getSimpleName());
    }
}
