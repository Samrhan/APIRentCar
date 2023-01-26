package com.ssa.ports.web.rentral.dto.response;

import com.ssa.domain.rental.model.Reservation;
import com.ssa.ports.web.catalog.dto.response.CarResponse;

import java.util.Date;
import java.util.UUID;

public class ReservationResponse {
    private final UUID id;
    private final CarResponse car;
    private final Date startDate;
    private final Date endDate;
    private final Boolean paid;

    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.car = new CarResponse(reservation.getCar());
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.paid = reservation.getPaid();
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

    public Boolean getPaid() {
        return paid;
    }
}
