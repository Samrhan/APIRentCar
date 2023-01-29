package com.ssa.controllers.http.rentral.dto.response;

import com.ssa.controllers.http.catalog.dto.response.CarResponse;
import com.ssa.domain.rental.model.CartEntry;

import java.util.Date;
import java.util.UUID;

public class CartEntryResponse {

    private final UUID id;
    private final CarResponse car;
    private final Date startDate;
    private final Date endDate;

    public CartEntryResponse(CartEntry cartEntry) {
        this.id = cartEntry.getId();
        this.car = new CarResponse(cartEntry.getCar());
        this.startDate = cartEntry.getStartDate();
        this.endDate = cartEntry.getEndDate();
    }

    public UUID getId() {
        return id;
    }

    public CarResponse getCar() {
        return car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
