package com.ssa.controllers.http.rentral.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

public class CartEntryRequest {

    private final UUID carId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final Date endDate;

    @JsonCreator
    public CartEntryRequest(@JsonProperty("carId") UUID carId, @JsonProperty("startDate") Date startDate, @JsonProperty("endDate") Date endDate) {
        this.carId = carId;
        this.startDate = startDate;
        this.endDate = endDate;
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
}
