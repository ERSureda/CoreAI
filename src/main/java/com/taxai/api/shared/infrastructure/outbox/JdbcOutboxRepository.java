package com.taxai.api.shared.infrastructure.outbox;

import com.girlocal.api.shared.domain.event.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcOutboxRepository implements OutboxRepository {

    private static final String INSERT_SQL = """
        INSERT INTO outbox (id, aggregate_type, aggregate_id, event_type, payload, occurred_at, processed, attempts, next_attempt_at)
        VALUES (:id, :aggregateType, :aggregateId, :eventType, :payload::jsonb, :occurredAt, false, 0, :occurredAt)
        """;
        
    private static final String MARK_PROCESSED_SQL = "UPDATE outbox SET processed = true WHERE id = :id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void append(DomainEvent event) {
        jdbcTemplate.update(INSERT_SQL, createParams(event));
    }

    @Override
    public void appendAll(List<DomainEvent> events) {
        if (events == null || events.isEmpty()) return;

        MapSqlParameterSource[] batch = events.stream()
            .map(this::createParams)
            .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(INSERT_SQL, batch);
    }

    @Override
    public List<UUID> findPendingEvents(int limit) {
        String sql = """
            UPDATE outbox
            SET next_attempt_at = now() + interval '5 minutes'
            WHERE id IN (
                SELECT id 
                FROM outbox 
                WHERE processed = false AND next_attempt_at <= now()
                ORDER BY occurred_at ASC
                LIMIT :limit
                FOR UPDATE SKIP LOCKED
            )
            RETURNING id
            """;
            
        return jdbcTemplate.queryForList(sql, new MapSqlParameterSource("limit", limit), UUID.class);
    }

    @Override
    public OutboxMessage getEventPayload(UUID id) {
        String sql = "SELECT event_type, payload FROM outbox WHERE id = :id";
        
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), (rs, rowNum) -> 
            new OutboxMessage(rs.getString("event_type"), rs.getString("payload"))
        );
    }

    @Override
    public void markPublished(UUID id) {
        jdbcTemplate.update(MARK_PROCESSED_SQL, new MapSqlParameterSource("id", id));
    }

    @Override
    public void markFailed(UUID id) {
        jdbcTemplate.update(MARK_PROCESSED_SQL, new MapSqlParameterSource("id", id));
    }

    @Override
    public void scheduleRetry(UUID id, Instant nextAttemptAt) {
        String sql = "UPDATE outbox SET next_attempt_at = :nextAttemptAt, attempts = attempts + 1 WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("nextAttemptAt", nextAttemptAt.atOffset(ZoneOffset.UTC))
            .addValue("id", id);
            
        jdbcTemplate.update(sql, params);
    }

    @Override
    public int currentAttempts(UUID id) {
        String sql = "SELECT attempts FROM outbox WHERE id = :id";
        Integer attempts = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), Integer.class);
        return attempts != null ? attempts : 0;
    }

    private MapSqlParameterSource createParams(DomainEvent event) {
        try {
            return new MapSqlParameterSource()
                .addValue("id", event.eventId())
                .addValue("aggregateType", event.getClass().getSimpleName())
                .addValue("aggregateId", event.aggregateId())
                .addValue("eventType", event.eventType())
                .addValue("payload", objectMapper.writeValueAsString(event))
                .addValue("occurredAt", event.occurredAt().atOffset(ZoneOffset.UTC));
        } catch (Exception e) {
            log.error("Failed to serialize outbox event {}", event.eventId(), e);
            throw new RuntimeException("Error serializing outbox event", e);
        }
    }
}
