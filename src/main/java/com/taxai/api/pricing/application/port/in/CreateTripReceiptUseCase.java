package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.ReceiptResult;
import com.taxai.api.pricing.infrastructure.adapter.in.web.dto.CreateTripReceiptHttpRequest;
import java.util.UUID;

public interface CreateTripReceiptUseCase { ReceiptResult execute(UUID tripId, CreateTripReceiptHttpRequest request); }
