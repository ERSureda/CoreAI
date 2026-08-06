package com.taxai.api.booking.application.service;

import com.taxai.api.booking.application.command.CreateBookingCommand;
import com.taxai.api.booking.application.port.in.CreateBookingUseCase;
import com.taxai.api.booking.application.port.out.BookingPort;
import com.taxai.api.booking.application.result.BookingResult;
import com.taxai.api.booking.domain.model.Booking;
import com.taxai.api.shared.infrastructure.utils.UuidGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CreateBookingUseCaseImpl implements CreateBookingUseCase {

    private final BookingPort bookingPort;

    @Override
    public BookingResult execute(CreateBookingCommand command) {
        UUID resetToken = UuidGenerator.generateId();
        //Booking booking = Booking.create();
        //bookingPort.save(booking)
        //return new BookingResult();
        return null;
    }
}
