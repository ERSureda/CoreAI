package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.command.ListVehiclesCommand;
import com.taxai.api.fleet.application.result.ListVehiclesResult;

public interface ListVehiclesUseCase {
    ListVehiclesResult execute(ListVehiclesCommand command);
}
