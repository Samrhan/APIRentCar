package com.bader.domain.rental.repository;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {
    List<Reservation> getReservationsBetweenForCar(UUID carId, Date startDate, Date endDate);

    void convertCartToReservationsAfterPayment(List<CartEntry> cart);

    List<Reservation> getReservationsForCustomerAfter(String associatedUserUsername, Date date);

    List<Reservation> getReservationsForCustomerBefore(String associatedUserUsername, Date date);
}
