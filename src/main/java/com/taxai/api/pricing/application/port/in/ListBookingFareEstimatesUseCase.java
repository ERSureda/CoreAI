package com.taxai.api.pricing.application.port.in;

import com.taxai.api.pricing.application.result.ListFareEstimatesResult;
import java.util.UUID;

public interface ListBookingFareEstimatesUseCase { ListFareEstimatesResult execute(UUID bookingId); }
