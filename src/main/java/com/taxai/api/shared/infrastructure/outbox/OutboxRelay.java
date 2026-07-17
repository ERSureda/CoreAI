package com.taxai.api.shared.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class OutboxRelay {

    private final OutboxRepository outboxRepository;
    private final OutboxWorker outboxWorker;
    private final ExecutorService executor;

    private static final int MAX_RETRIES = 5;
    private static final int BATCH_SIZE = 100;

    public OutboxRelay(OutboxRepository outboxRepository, OutboxWorker outboxWorker) {
        this.outboxRepository = outboxRepository;
        this.outboxWorker = outboxWorker;
        this.executor = Executors.newFixedThreadPool(10);
    }

    @Scheduled(fixedDelay = 5000)
    public void processPendingEvents() {
        for (UUID id : outboxRepository.findPendingEvents(BATCH_SIZE)) {
            CompletableFuture.runAsync(() -> {
                try {
                    outboxWorker.process(id);
                } catch (Exception e) {
                    log.error("Error processing outbox event {}", id, e);
                    outboxWorker.recordFailure(id);
                }
            }, executor);
        }
    }
}
