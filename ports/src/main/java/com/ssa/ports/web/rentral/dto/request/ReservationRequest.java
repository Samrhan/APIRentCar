package com.ssa.ports.web.rentral.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class ReservationRequest {
    private final UUID customerId;

    private final UUID carId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Date endDate;

    private final Boolean paid;

    @JsonCreator
    public ReservationRequest(@JsonProperty("customerId") UUID customerId, @JsonProperty("carId") UUID carId, @JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate, @JsonProperty("paid") Boolean paid) {
        this.customerId = customerId;
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paid = paid;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getCarId() {
        return carId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Boolean getPaid() {
        return paid;
    }
}
