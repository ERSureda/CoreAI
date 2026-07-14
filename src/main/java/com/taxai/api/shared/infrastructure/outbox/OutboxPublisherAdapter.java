package com.taxai.api.shared.infrastructure.outbox;

import com.girlocal.api.shared.application.port.out.OutboxPublisherPort;
import com.girlocal.api.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxPublisherAdapter implements OutboxPublisherPort {

    private final OutboxRepository outbox;

    @Override
    public void record(DomainEvent domainEvent) {
        outbox.append(domainEvent);
    }

    @Override
    public void recordAll(List<DomainEvent> domainEvents) {
        for (DomainEvent domainEvent : domainEvents) {
            outbox.append(domainEvent);
        }
    }
}
