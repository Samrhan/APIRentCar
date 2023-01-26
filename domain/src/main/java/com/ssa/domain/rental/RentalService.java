package com.ssa.domain.rental;

import com.ssa.domain.rental.model.CartEntry;
import com.ssa.domain.rental.model.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalService {
    List<CartEntry> getCart(String associatedUserUsername);

    boolean deleteCart(String associatedUserUsername);

    Optional<CartEntry> addCartEntry(String associatedUserUsername, UUID carId, Date startDate, Date endDate);

    boolean deleteCartEntry(String associatedUserUsername, UUID cartEntryId);

    List<Reservation> getReservationsBetweenForCar(UUID carId, Date searchStartDate, Date searchEndDate);

    boolean payCart(String associatedUserUsername, String cardNumber, String securityCode, String expirationDate, String ownerName);

    List<Reservation> getFutureReservationsForCustomer(String associatedUserUsername);

    List<Reservation> getPastReservationsForCustomer(String associatedUserUsername);

    List<Reservation> getAllReservationsForCustomer(String associatedUserUsername);

    Optional<Reservation> addReservation(UUID customerId, UUID carId, Date startDate, Date endDate, Boolean paid);

    void payReservationOnSite(UUID reservationId);
}
