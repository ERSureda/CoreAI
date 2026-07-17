package com.taxai.api.shared.infrastructure.outbox;

import com.taxai.api.shared.application.port.out.EventDispatcherPort;
import com.taxai.api.shared.application.port.out.EventSerializerPort;
import com.taxai.api.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxWorker {

    private final OutboxRepository outboxRepository;
    private final EventDispatcherPort eventDispatcher;
    private final EventSerializerPort eventSerializer;

    private static final int MAX_ATTEMPTS = 5;
    private static final Duration BASE_BACKOFF = Duration.ofSeconds(2);
    private static final Duration MAX_BACKOFF = Duration.ofMinutes(10);

    @Transactional
    public void process(UUID id) {
        OutboxRepository.OutboxMessage message = outboxRepository.getEventPayload(id);
        DomainEvent event = eventSerializer.deserialize(message.eventType(), message.payload());
        
        eventDispatcher.dispatch(event);
        outboxRepository.markPublished(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailure(UUID id) {
        int nextAttempt = outboxRepository.currentAttempts(id) + 1;
        if (nextAttempt >= MAX_ATTEMPTS) {
            log.error("Outbox event {} exceeded max retries. Marking as failed.", id);
            outboxRepository.markFailed(id);
        } else {
            outboxRepository.scheduleRetry(id, Instant.now().plus(backoff(nextAttempt)));
        }
    }

    private Duration backoff(int attempt) {
        Duration duration = BASE_BACKOFF.multipliedBy(1L << Math.min(attempt - 1, 20));
        return duration.compareTo(MAX_BACKOFF) > 0 ? MAX_BACKOFF : duration;
    }
}
