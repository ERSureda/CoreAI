package com.taxai.api.shared.infrastructure.outbox;

import com.girlocal.api.shared.domain.event.DomainEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OutboxRepository {

    record OutboxMessage(String eventType, String payload) {}

    void append(DomainEvent event);
    void appendAll(List<DomainEvent> events);
    List<UUID> findPendingEvents(int limit);
    OutboxMessage getEventPayload(UUID id);
    void markPublished(UUID id);
    void markFailed(UUID id);
    void scheduleRetry(UUID id, Instant nextAttemptAt);
    int currentAttempts(UUID id);
}
