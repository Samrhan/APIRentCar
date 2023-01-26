package com.ssa.ports.web.rentral.dto.response;

import com.ssa.domain.rental.model.Reservation;

import java.util.Date;

public class AnonymousReservationResponse {
    private final Date startDate;
    private final Date endDate;

    public AnonymousReservationResponse(Reservation reservation) {
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
