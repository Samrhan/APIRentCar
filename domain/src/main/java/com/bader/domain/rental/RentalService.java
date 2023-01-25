package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;

import java.util.*;

public interface RentalService {
    List<CartEntry> getCart(String associatedUserUsername);

    boolean deleteCart(String associatedUserUsername);

    Optional<CartEntry> addCartEntry(String associatedUserUsername, UUID carId, Date startDate, Date endDate);

    boolean deleteCartEntry(String associatedUserUsername, UUID cartEntryId);

    List<Reservation> getFutureReservationsForCar(UUID carId);
}
