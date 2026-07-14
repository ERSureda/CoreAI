package com.taxai.api.shared.domain.event;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID eventId();
    String aggregateId();
    Instant occurredAt();
    String eventType();
}
