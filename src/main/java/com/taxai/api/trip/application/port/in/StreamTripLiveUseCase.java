package com.taxai.api.trip.application.port.in;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.UUID;

public interface StreamTripLiveUseCase { SseEmitter execute(UUID tripId); }
