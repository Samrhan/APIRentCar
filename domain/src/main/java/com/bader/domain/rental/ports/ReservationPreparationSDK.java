package com.bader.domain.rental.ports;

import com.bader.domain.rental.model.Reservation;

public interface ReservationPreparationSDK {
    void prepareReservation(Reservation reservation);
}
