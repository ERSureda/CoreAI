package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.StreamTripLiveCommand;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamTripLiveUseCase {
    SseEmitter execute(StreamTripLiveCommand command);
}
