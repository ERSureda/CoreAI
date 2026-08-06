package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.adapter;

import com.taxai.api.booking.application.port.out.BookingPort;
import com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookingPersistanceAdapter implements BookingPort {

    private final BookingRepository bookingRepository;

}
