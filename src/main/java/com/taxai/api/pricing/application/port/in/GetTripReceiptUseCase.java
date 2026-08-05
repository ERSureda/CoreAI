package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.ReceiptResult;
import java.util.UUID;

public interface GetTripReceiptUseCase { ReceiptResult execute(UUID tripId); }
