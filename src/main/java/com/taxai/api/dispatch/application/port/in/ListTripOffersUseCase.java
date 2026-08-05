package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.ListOffersResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.ListTripOffersHttpRequest;
import java.util.UUID;

public interface ListTripOffersUseCase { ListOffersResult execute(UUID tripId, ListTripOffersHttpRequest request); }
