package com.taxai.api.pricing.domain.model;

import com.taxai.api.shared.domain.model.AggregateRoot;
import com.taxai.api.shared.domain.model.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Tariff extends AggregateRoot<UUID> {

    private final String name;
    private final UUID zoneId;
    private final VehicleType vehicleType;
    private final BigDecimal baseFare;
    private final BigDecimal perKm;
    private final BigDecimal perMin;
    private final BigDecimal minFare;
    private final BigDecimal waitingPerMin;
    private final BigDecimal nightSurchargePct;
    private final BigDecimal airportSurcharge;
    private final LocalDate validFrom;
    private final LocalDate validTo;
    private Boolean active;

    /// --- Constructors ---
    private Tariff(
            UUID id,
            String name,
            UUID zoneId,
            VehicleType vehicleType,
            BigDecimal baseFare,
            BigDecimal perKm,
            BigDecimal perMin,
            BigDecimal minFare,
            BigDecimal waitingPerMin,
            BigDecimal nightSurchargePct,
            BigDecimal airportSurcharge,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean active
    ) {
        this.id = id;
        this.name = name;
        this.zoneId = zoneId;
        this.vehicleType = vehicleType;
        this.baseFare = baseFare;
        this.perKm = perKm;
        this.perMin = perMin;
        this.minFare = minFare;
        this.waitingPerMin = waitingPerMin;
        this.nightSurchargePct = nightSurchargePct;
        this.airportSurcharge = airportSurcharge;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;

       // this.validateData();
    }

    public static Tariff create(
            UUID id,
            String name,
            UUID zoneId,
            VehicleType vehicleType,
            BigDecimal baseFare,
            BigDecimal perKm,
            BigDecimal perMin,
            BigDecimal minFare,
            BigDecimal waitingPerMin,
            BigDecimal nightSurchargePct,
            BigDecimal airportSurcharge,
            LocalDate validFrom,
            LocalDate validTo
    ) {
        return new Tariff(
                id,
                name,
                zoneId,
                vehicleType,
                baseFare,
                perKm,
                perMin,
                minFare,
                waitingPerMin,
                nightSurchargePct,
                airportSurcharge,
                validFrom,
                validTo,
                true
        );
    }

    public static Tariff reconstruct(
            UUID id,
            String name,
            UUID zoneId,
            VehicleType vehicleType,
            BigDecimal baseFare,
            BigDecimal perKm,
            BigDecimal perMin,
            BigDecimal minFare,
            BigDecimal waitingPerMin,
            BigDecimal nightSurchargePct,
            BigDecimal airportSurcharge,
            LocalDate validFrom,
            LocalDate validTo,
            Boolean active
    ) {
        return new Tariff(
                id,
                name,
                zoneId,
                vehicleType,
                baseFare,
                perKm,
                perMin,
                minFare,
                waitingPerMin,
                nightSurchargePct,
                airportSurcharge,
                validFrom,
                validTo,
                active
        );
    }

    /// --- Getters ---
    public String getName() {
        return name;
    }

    public UUID getZoneId() {
        return zoneId;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public BigDecimal getBaseFare() {
        return baseFare;
    }

    public BigDecimal getPerKm() {
        return perKm;
    }

    public BigDecimal getPerMin() {
        return perMin;
    }

    public BigDecimal getMinFare() {
        return minFare;
    }

    public BigDecimal getWaitingPerMin() {
        return waitingPerMin;
    }

    public BigDecimal getNightSurchargePct() {
        return nightSurchargePct;
    }

    public BigDecimal getAirportSurcharge() {
        return airportSurcharge;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public Boolean getActive() {
        return active;
    }

    /// --- Business Logic ---
}
