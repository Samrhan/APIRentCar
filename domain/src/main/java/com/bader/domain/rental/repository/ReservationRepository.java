package com.bader.domain.rental.repository;

import com.bader.domain.rental.model.Reservation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {
    List<Reservation> getReservationsBetweenForCar(UUID carId, Date startDate, Date endDate);

    List<Reservation> getFutureReservationsForCar(UUID carId);
}
