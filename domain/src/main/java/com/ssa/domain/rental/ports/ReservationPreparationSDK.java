package com.ssa.domain.rental.ports;

import com.ssa.domain.rental.model.Reservation;

public interface ReservationPreparationSDK {
    void prepareReservation(Reservation reservation);
}
