package com.taxai.api.shared.infrastructure.serialization;

import com.girlocal.api.shared.application.port.out.EventSerializerPort;
import com.girlocal.api.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class JacksonEventSerializer implements EventSerializerPort {

    private final ObjectMapper objectMapper;

    @Override
    public String serialize(DomainEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event: " + event.eventId(), e);
        }
    }

    @Override
    public DomainEvent deserialize(String eventType, String payload) {
        try {
            Class<?> clazz = Class.forName(eventType);
            if (!DomainEvent.class.isAssignableFrom(clazz)) {
                throw new IllegalArgumentException("Class " + eventType + " does not implement DomainEvent");
            }
            return (DomainEvent) objectMapper.readValue(payload, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize event of type: " + eventType, e);
        }
    }
}
