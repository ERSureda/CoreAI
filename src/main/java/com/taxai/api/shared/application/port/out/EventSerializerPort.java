package com.taxai.api.shared.application.port.out;

import com.girlocal.api.shared.domain.event.DomainEvent;

public interface EventSerializerPort {

    String serialize(DomainEvent event);
    DomainEvent deserialize(String eventType, String payload);
}
