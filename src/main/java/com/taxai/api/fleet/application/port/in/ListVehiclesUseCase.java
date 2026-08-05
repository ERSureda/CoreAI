package com.taxai.api.fleet.application.port.in;

import com.taxai.api.fleet.application.result.ListVehiclesResult;
import com.taxai.api.fleet.infrastructure.adapter.in.web.dto.ListVehiclesHttpRequest;

public interface ListVehiclesUseCase {
    ListVehiclesResult execute(ListVehiclesHttpRequest request);
}
