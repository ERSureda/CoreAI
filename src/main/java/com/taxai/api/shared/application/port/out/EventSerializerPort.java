package com.taxai.api.shared.application.port.out;

import com.taxai.api.shared.domain.event.DomainEvent;

public interface EventSerializerPort {

    String serialize(DomainEvent event);
    DomainEvent deserialize(String eventType, String payload);
}
