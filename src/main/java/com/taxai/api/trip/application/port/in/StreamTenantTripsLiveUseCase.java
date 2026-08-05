package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.command.StreamTenantTripsLiveCommand;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface StreamTenantTripsLiveUseCase {
    SseEmitter execute(StreamTenantTripsLiveCommand command);
}
