package com.taxai.api.booking.application.result;

import java.util.List;

public record ListBookingsResult(
        List<BookingItem> items,
        String nextCursor
) {}
