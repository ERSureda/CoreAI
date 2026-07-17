package com.taxai.api.shared.infrastructure.messaging;

import com.taxai.api.shared.application.port.out.EventDispatcherPort;
import com.taxai.api.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InProcessEventDispatcher implements EventDispatcherPort {

    private final ApplicationEventPublisher  applicationEventPublisher;

    @Override
    public void dispatch(DomainEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
