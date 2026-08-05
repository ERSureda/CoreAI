package com.taxai.api.booking.application.port.in;

import com.taxai.api.booking.application.result.*;
import com.taxai.api.booking.infrastructure.adapter.in.web.dto.*;
import java.util.UUID;

public interface CreateBookingUseCase {
    BookingResult execute(CreateBookingHttpRequest request);
}
