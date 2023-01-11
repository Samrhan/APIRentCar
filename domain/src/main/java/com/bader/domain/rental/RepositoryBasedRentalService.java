package com.bader.domain.rental;

import com.bader.domain.rental.model.CartEntry;
import com.bader.domain.rental.model.Reservation;
import com.bader.domain.rental.repository.CartEntryRepository;
import com.bader.domain.rental.repository.ReservationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RepositoryBasedRentalService implements RentalService {

    private final CartEntryRepository cartEntryRepository;
    private final ReservationRepository reservationRepository;

    public RepositoryBasedRentalService(CartEntryRepository cartEntryRepository, ReservationRepository reservationRepository) {
        this.cartEntryRepository = cartEntryRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<CartEntry> getCart() {
        return this.cartEntryRepository.getCart();
    }

    @Override
    public boolean deleteCart() {
        return this.cartEntryRepository.deleteCart();
    }

    @Override
    public Optional<CartEntry> addCartEntry(UUID carId, Date startDate, Date endDate) {
        if(isCarAvailableBetween(carId, startDate, endDate)){
            return Optional.ofNullable(this.cartEntryRepository.addCartEntry(carId, startDate, endDate));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteCartEntry(UUID cartEntryId) {
        return this.cartEntryRepository.deleteCartEntry(cartEntryId);
    }

    @Override
    public List<Reservation> getFutureReservationsForCar(UUID carId) {
        return this.reservationRepository.getFutureReservationsForCar(carId);
    }

    private boolean isCarAvailableBetween(UUID carId, Date startDate, Date endDate) {
        List<Reservation> carReservations = this.reservationRepository.getReservationsBetweenForCar(carId, startDate, endDate);
        return carReservations.size() == 0;
    }
}
