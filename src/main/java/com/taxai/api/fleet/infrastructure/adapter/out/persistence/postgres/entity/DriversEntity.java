package com.taxai.api.fleet.infrastructure.adapter.out.persistence.postgres.entity;

import com.taxai.api.fleet.domain.model.enums.DriverAdminStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "drivers", schema = "fleet",
        uniqueConstraints = {
                @UniqueConstraint(name = "drivers_tenant_id_employee_code_key", columnNames = {"tenant_id", "employee_code"}),
                @UniqueConstraint(name = "drivers_tenant_id_phone_key", columnNames = {"tenant_id", "phone"}),
                @UniqueConstraint(name = "drivers_user_id_key", columnNames = {"user_id"})
        },
        indexes = {
                @Index(name = "ix_drivers_tenant_status", columnList = "tenant_id, admin_status")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DriversEntity {

    @Id
    private UUID id;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "admin_status", nullable = false)
    private DriverAdminStatus adminStatus;

    @Column(name = "default_vehicle_id")
    private UUID defaultVehicleId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
