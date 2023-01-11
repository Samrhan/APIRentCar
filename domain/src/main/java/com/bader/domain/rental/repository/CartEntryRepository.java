package com.bader.domain.rental.repository;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartEntryRepository {
    List<CartEntry> getCart();

    Optional<CartEntry> addCartEntry(UUID carId, Date startDate, Date endDate);

    boolean deleteCart();

    boolean deleteCartEntry(UUID cartEntryId);

    List<Reservation> getReservationsBetween(UUID carId, Date startDate, Date endDate);
}
