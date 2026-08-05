package com.taxai.api.dispatch.application.port.in;

import com.taxai.api.dispatch.application.result.OfferResult;
import com.taxai.api.dispatch.infrastructure.adapter.in.web.dto.AssignNearestDriverHttpRequest;
import java.util.UUID;

public interface AssignNearestDriverUseCase { OfferResult execute(UUID tripId, AssignNearestDriverHttpRequest request); }
