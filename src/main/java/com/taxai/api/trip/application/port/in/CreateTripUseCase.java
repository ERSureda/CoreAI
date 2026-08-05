package com.taxai.api.trip.application.port.in;

import com.taxai.api.trip.application.result.*;
import com.taxai.api.trip.infrastructure.adapter.in.web.dto.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.UUID;

public interface CreateTripUseCase { TripResult execute(CreateTripHttpRequest request); }
