package com.taxai.api.shared.application.port.out;

import com.taxai.api.shared.domain.event.DomainEvent;

public interface EventDispatcherPort {
    void dispatch(DomainEvent event);
}
