package com.taxai.api.booking.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.booking.domain.model.enums.LuggageType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "booking_luggage", schema = "booking",
        indexes = {
                @Index(name = "ix_booking_luggage", columnList = "booking_id")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class BookingLuggageEntity {

    @Id
    private UUID id;

    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "type", nullable = false)
    private LuggageType type;

    @Column(name = "quantity", nullable = false)
    private Short quantity;

    @Column(name = "notes")
    private String notes;
}
