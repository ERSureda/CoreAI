package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.ListTripsResult;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.ListTripsHttpRequest;

public interface ListTripsUseCase { ListTripsResult execute(ListTripsHttpRequest request); }
