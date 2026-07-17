package com.taxai.api.shared.application.port.out;

import com.taxai.api.shared.domain.event.DomainEvent;

import java.util.List;

public interface OutboxPublisherPort {
    void record(DomainEvent domainEvent);
    void recordAll(List<DomainEvent> domainEvents);
}
