package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;

import java.util.*;

public interface RentalService {
    List<CartEntry> getCart();

    boolean deleteCart();

    Optional<CartEntry> addCartEntry(UUID carId, Date startDate, Date endDate);

    boolean deleteCartEntry(UUID cartEntryId);

    List<Reservation> getFutureReservationsForCar(UUID carId);
}
