package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.ListBookingsResult;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.ListBookingsHttpRequest;

public interface ListBookingsUseCase {
    ListBookingsResult execute(ListBookingsHttpRequest request);
}
