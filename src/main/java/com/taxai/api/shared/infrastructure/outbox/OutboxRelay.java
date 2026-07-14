package com.taxai.api.shared.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelay {

    private final OutboxRepository outboxRepository;
    private final OutboxWorker outboxWorker;

    private static final int MAX_RETRIES = 5;
    private static final int BATCH_SIZE = 100;

    @Scheduled(fixedDelay = 5000)
    public void processPendingEvents() {
        for (UUID id : outboxRepository.findPendingEvents(BATCH_SIZE)) {
            try {
                outboxWorker.process(id);
            } catch (Exception e) {
                log.error("Error processing outbox event {}", id, e);
                outboxWorker.recordFailure(id);
            }
        }
    }
}
