package com.taxai.api.trip.domain.model.enums;

public enum CancelReason {
    PASSENGER_NO_SHOW,
    DRIVER_NO_SHOW,
    PASSENGER_REQUESTED,
    DRIVER_REQUESTED,
    OPERATOR_REQUESTED,
    SYSTEM_TIMEOUT,
    VEHICLE_BREAKDOWN
}