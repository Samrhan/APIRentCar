package com.bader.ports.web.rentral.dto.response;

import com.bader.domain.rental.model.CartEntry;
import com.bader.ports.web.catalog.dto.response.CarResponse;

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
